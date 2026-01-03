// Загрузка продуктов
async function loadProducts() {
    try {
        const response = await fetch(`${API_URL}/products`);

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        const products = await response.json();
        const productsList = document.getElementById('products-list');

        // Проверяем структуру ответа
        const productsArray = products.data || products.content || products;

        if (!Array.isArray(productsArray)) {
            console.error('Products is not an array:', productsArray);
            productsList.innerHTML = '<p>No products available</p>';
            return;
        }

        displayProducts(productsArray);
    } catch (error) {
        console.error('Error loading products:', error);
        document.getElementById('products-list').innerHTML =
            `<p style="color: red;">Error loading products: ${error.message}</p>`;
    }
}

// Отображение продуктов
function displayProducts(products) {
    const productsList = document.getElementById('products-list');
    productsList.innerHTML = '';

    products.forEach(product => {
        const productDiv = document.createElement('div');
        productDiv.className = 'product-card';
        productDiv.innerHTML = `
            <h3>${product.name}</h3>
            <p>${product.description || 'No description'}</p>
            <p><strong>Price: $${product.price || '0.00'}</strong></p>
            <button onclick="addToCart('${product.id}')" class="btn btn-outline">Add to Cart</button>
        `;
        productsList.appendChild(productDiv);
    });
}