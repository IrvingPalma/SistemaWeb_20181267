import React from 'react';
import { useNavigate } from 'react-router-dom';
import { FaUserCheck, FaShip, FaMoneyBillWave } from 'react-icons/fa'; // Importa los iconos que necesitas
import './Sidebar.css';
import foto from '../assets/foto.png'; 

const Sidebar = ({ setIsAuthenticated }) => {
  const navigate = useNavigate();

  const handleLogout = () => {
    setIsAuthenticated(false);
    navigate('/login');
  };

  return (
    <div className="sidebar">
      <div className="profile-info">
        <img src={foto} />
        <span className="profile-name">Irving Palma Salas</span>
      </div>
      <div className="divider"></div> {/* Separador */}
      <div className="navigation-buttons">
        <button onClick={() => navigate('/inspectors')}>
          <FaUserCheck /> Gestión de Inspectores
        </button>
        <button onClick={() => navigate('/boats')}>
          <FaShip /> Gestión de Embarcaciones
        </button>
        <button onClick={() => navigate('/fines')}>
          <FaMoneyBillWave /> Gestión de Multas
        </button>
      </div>
      <div className="divider"></div> {/* Separador */}
      <div className="logout-section">
        <button className="logout-button" onClick={handleLogout}>Cerrar sesión</button>
      </div>
    </div>
  );
};

export default Sidebar;
