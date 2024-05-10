import React, { useState } from 'react';
import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
import LoginPage from './components/LoginPage';
import Sidebar from './components/Sidebar';
import InspectorManagement from './components/InspectorManagement';
import EmbarcacionesManagement from './components/EmbarcacionesManagement';

function App() {
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  const handleLogin = (credentials) => {
    if (credentials.username === 'irving.palma@pucp.edu.pe' && credentials.password === '123456') {
      setIsAuthenticated(true);
    } else {
      setIsAuthenticated(false);
    }
  }

  return (
    <Router>
      <div style={{ display: 'flex' }}>
        {isAuthenticated && <Sidebar setIsAuthenticated={setIsAuthenticated} />}
        <div style={{ flex: 1 }}>
          <Routes>
            <Route path="/login" element={<LoginPage onLogin={handleLogin} isAuthenticated={isAuthenticated} />} />
            <Route path="/inspectors" element={isAuthenticated ? <InspectorManagement /> : <Navigate to="/login" />} />
            <Route path="/boats" element={isAuthenticated ? <EmbarcacionesManagement /> : <Navigate to="/login" />} />
            <Route path="/" element={<Navigate to={isAuthenticated ? "/inspectors" : "/login"} />} />
          </Routes>
        </div>
      </div>
    </Router>
  );
}

export default App;
