import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { AiOutlineEye, AiOutlineEyeInvisible } from 'react-icons/ai';
import './LoginPage.css';

const LoginPage = ({ onLogin, isAuthenticated }) => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [showPassword, setShowPassword] = useState(false);
  const navigate = useNavigate();

  const togglePasswordVisibility = () => {
    setShowPassword(!showPassword);
  };

  const handleLogin = (event) => {
    event.preventDefault();
    onLogin({ username: email, password: password });
  };

  if (isAuthenticated) {
    navigate('/inspectors');
  }

  return (
    <div className="login-container">
      <div className="welcome-message">
        <h1>Bienvenido(a)</h1>
      </div>
      <div className="login-form">
        <h1 style={{color: "#000000"}}>Iniciar sesión</h1>
        <form onSubmit={handleLogin}>
          <div className="input-container">
            <label htmlFor="email">Correo</label>
            <input type="email" id="email" name="email" required value={email} onChange={(e) => setEmail(e.target.value)} />
          </div>
          <div className="input-container">
            <label htmlFor="password">Contraseña</label>
            <input type={showPassword ? "text" : "password"} id="password" name="password" required value={password} onChange={(e) => setPassword(e.target.value)} />
            <span onClick={togglePasswordVisibility} className="password-icon">
              {showPassword ? <AiOutlineEyeInvisible /> : <AiOutlineEye />}
            </span>
          </div>
          <div className="forgot-password">
            <a href="#">¿Olvidaste tu contraseña?</a>
          </div>
          <button type="submit" className="login-button">Ingresar</button>
        </form>
      </div>
    </div>
  );
};

export default LoginPage;
