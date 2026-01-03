// Инициализация при загрузке страницы
window.onload = function() {
    // Обновляем UI авторизации
    updateAuthUI();

    // Загружаем продукты
    loadProducts();

    // Загружаем корзину если авторизован
    if (isAuthenticated()) {
        loadCart();
    }

    // Навешиваем обработчики
    const loginBtn = document.getElementById('login-btn');
    const logoutBtn = document.getElementById('logout-btn');
    const checkoutBtn = document.getElementById('checkout-btn');

    if (loginBtn) {
        loginBtn.addEventListener('click', () => {
            window.location.href = 'login.html';
        });
    }

    if (logoutBtn) {
        logoutBtn.addEventListener('click', logout);
    }

    if (checkoutBtn) {
        checkoutBtn.addEventListener('click', checkout);
    }
};