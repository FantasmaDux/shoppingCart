const API_URL = 'http://localhost:8080/api/v1';

// Функция логина
async function login(email, password) {
    try {
        const response = await fetch(`${API_URL}/auth/login`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ email, password })
        });

        const data = await response.json();

        if (response.ok) {
            // Сохраняем токен и email
            const token = data.data?.token || data.token;
            if (token) {
                localStorage.setItem('token', token);
                localStorage.setItem('userEmail', email);
                return { success: true, token: token };
            }
            return { success: false, error: 'No token received' };
        } else {
            return { success: false, error: data.message || 'Login failed' };
        }
    } catch (error) {
        return { success: false, error: error.message };
    }
}

// Функция выхода
function logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('userEmail');
    window.location.href = 'index.html';
}

// Проверка авторизации
function isAuthenticated() {
    return !!localStorage.getItem('token');
}

// Получение email пользователя
function getUserEmail() {
    return localStorage.getItem('userEmail') || '';
}

// Обновление UI в зависимости от авторизации
function updateAuthUI() {
    const token = localStorage.getItem('token');
    const userEmail = localStorage.getItem('userEmail');

    const loginBtn = document.getElementById('login-btn');
    const logoutBtn = document.getElementById('logout-btn');
    const userEmailDiv = document.getElementById('user-email');
    const emailDisplay = document.getElementById('email-display');
    const cartSection = document.getElementById('cart-section');
    const authBanner = document.getElementById('auth-banner');

    if (token && userEmail) {
        // Пользователь авторизован
        if (loginBtn) loginBtn.style.display = 'none';
        if (logoutBtn) logoutBtn.style.display = 'block';
        if (userEmailDiv) userEmailDiv.style.display = 'block';
        if (emailDisplay) emailDisplay.textContent = userEmail;
        if (cartSection) cartSection.style.display = 'block';
        if (authBanner) authBanner.style.display = 'none';
    } else {
        // Пользователь не авторизован
        if (loginBtn) loginBtn.style.display = 'block';
        if (logoutBtn) logoutBtn.style.display = 'none';
        if (userEmailDiv) userEmailDiv.style.display = 'none';
        if (cartSection) cartSection.style.display = 'none';
        if (authBanner) authBanner.style.display = 'block';
    }
}

// Проверка токена на сервере (опционально)
async function validateToken() {
    const token = localStorage.getItem('token');
    if (!token) return false;

    try {
        const response = await fetch(`${API_URL}/auth/validate`, {
            headers: { 'Authorization': `Bearer ${token}` }
        });
        return response.ok;
    } catch {
        return false;
    }
}