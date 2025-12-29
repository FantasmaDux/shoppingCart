const API_URL = 'http://localhost:8080/api/v1';

// 1. ЛОГИН
document.getElementById('login-form').addEventListener('submit', async function(e) {
    e.preventDefault();

    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    try {
        const response = await fetch(`${API_URL}/auth/login`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ email, password })
        });

        const data = await response.json();

        if (response.ok) {
            // Сохраняем токен
            const token = data.data.token;
            localStorage.setItem('token', token);
            document.getElementById('message').textContent = 'Login successful!';
            document.getElementById('message').style.color = 'green';

            // Показываем товары и корзину
            document.getElementById('login-section').style.display = 'none';
            document.getElementById('products-section').style.display = 'block';
            document.getElementById('cart-section').style.display = 'block';

            // Загружаем товары
            loadProducts();
            loadCart();
        } else {
            document.getElementById('message').textContent = 'Error: ' + data.message;
            document.getElementById('message').style.color = 'red';
        }
    } catch (error) {
        document.getElementById('message').textContent = 'Error: ' + error.message;
        document.getElementById('message').style.color = 'red';
    }
});

// 2. ЗАГРУЗКА ТОВАРОВ
async function loadProducts() {
    try {
        const token = localStorage.getItem('token');
        const response = await fetch(`${API_URL}/products`, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });

        const products = await response.json();
        const productsList = document.getElementById('products-list');
        productsList.innerHTML = '';

        products.forEach(product => {
            const productDiv = document.createElement('div');
            productDiv.className = 'product';
            productDiv.innerHTML = `
                <h3>${product.name}</h3>
                <p>${product.description || ''}</p>
                <p>Price: $${product.price}</p>
                <button onclick="addToCart(${product.id})">Add to Cart</button>
            `;
            productsList.appendChild(productDiv);
        });
    } catch (error) {
        console.error('Error loading products:', error);
    }
}

// 3. ДОБАВЛЕНИЕ В КОРЗИНУ
async function addToCart(productId) {
    try {
        const token = localStorage.getItem('token');
        await fetch(`${API_URL}/cartItems/item`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify({
                productId: productId,
                quantity: 1
            })
        });

        alert('Added to cart!');
        loadCart(); // Обновляем корзину
    } catch (error) {
        console.error('Error adding to cart:', error);
    }
}

// 4. ЗАГРУЗКА КОРЗИНЫ
async function loadCart() {
    try {
        const token = localStorage.getItem('token');
        const response = await fetch(`${API_URL}/cart`, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });

        const cart = await response.json();
        const cartItems = document.getElementById('cart-items');
        cartItems.innerHTML = '';

        if (cart.items && cart.items.length > 0) {
            cart.items.forEach(item => {
                const itemDiv = document.createElement('div');
                itemDiv.className = 'cart-item';
                itemDiv.innerHTML = `
                    <p>${item.productName} - $${item.price} x ${item.quantity}</p>
                `;
                cartItems.appendChild(itemDiv);
            });
        } else {
            cartItems.innerHTML = '<p>Your cart is empty</p>';
        }
    } catch (error) {
        console.error('Error loading cart:', error);
    }
}

// 5. ОФОРМЛЕНИЕ ЗАКАЗА
document.getElementById('checkout-btn').addEventListener('click', async function() {
    try {
        const token = localStorage.getItem('token');
        const response = await fetch(`${API_URL}/orders/create`, {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });

        if (response.ok) {
            alert('Order created successfully!');
            loadCart(); // Корзина должна очиститься
        }
    } catch (error) {
        console.error('Error creating order:', error);
    }
});

// 6. ПРИ ЗАГРУЗКЕ СТРАНИЦЫ ПРОВЕРЯЕМ ТОКЕН
window.onload = function() {
    const token = localStorage.getItem('token');
    if (token) {
        // Если есть токен, сразу показываем товары
        document.getElementById('login-section').style.display = 'none';
        document.getElementById('products-section').style.display = 'block';
        document.getElementById('cart-section').style.display = 'block';
        loadProducts();
        loadCart();
    }
};