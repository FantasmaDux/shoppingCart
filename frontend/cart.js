// Добавление в корзину
async function addToCart(productId) {
    const token = localStorage.getItem('token');

    if (!token) {
        alert('Please login to add items to cart!');
        window.location.href = 'login.html';
        return;
    }

    try {
        const response = await fetch(`${API_URL}/cartItems/item`, {
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

        if (response.ok) {
            alert('Added to cart!');
            loadCart();
        } else {
            const error = await response.json();
            alert('Error: ' + (error.message || 'Failed to add to cart'));
        }
    } catch (error) {
        console.error('Error adding to cart:', error);
        alert('Error adding to cart');
    }
}

// Загрузка корзины
async function loadCart() {
    const token = localStorage.getItem('token');

    if (!token) return;

    try {
        const response = await fetch(`${API_URL}/carts`, {
            headers: { 'Authorization': `Bearer ${token}` }
        });

        const cart = await response.json();
        const cartItems = document.getElementById('cart-items');
        cartItems.innerHTML = '';

        const cartData = cart.data || cart;
        const items = cartData.items || cartData.cartItems || [];

        if (items.length > 0) {
            items.forEach(item => {
                const itemDiv = document.createElement('div');
                itemDiv.className = 'cart-item';
                itemDiv.innerHTML = `
                    <p>${item.product?.name || item.productName} - $${item.price || '0.00'} x ${item.quantity || 1}</p>
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

// Оформление заказа
async function checkout() {
    const token = localStorage.getItem('token');

    if (!token) {
        alert('Please login to checkout!');
        window.location.href = 'login.html';
        return;
    }

    try {
        const response = await fetch(`${API_URL}/orders`, {
            method: 'POST',
            headers: { 'Authorization': `Bearer ${token}` }
        });

        if (response.ok) {
            alert('Order created successfully!');
            loadCart();
        } else {
            const error = await response.json();
            alert('Error: ' + (error.message || 'Failed to create order'));
        }
    } catch (error) {
        console.error('Error creating order:', error);
        alert('Error creating order');
    }
}