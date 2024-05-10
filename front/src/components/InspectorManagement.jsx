import React, { useState, useEffect } from 'react';
import axios from 'axios';
import CircularProgress from '@mui/material/CircularProgress';
import './InspectorManagement.css';
import { FaPencilAlt } from 'react-icons/fa';
import { FaSearch } from 'react-icons/fa';

const InspectorManagement = () => {
  const [inspectors, setInspectors] = useState([]);
  const [searchTerm, setSearchTerm] = useState('');
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const handleSearchChange = (event) => {
    setSearchTerm(event.target.value);
  };
  const handleSelectAll = (e) => {
    const newInspectores= inspectors.map(inspector => ({
      ...inspector,
      isSelected: e.target.checked
    }));
    setInspectors(newInspectores);
  };

  const handleKeyPress = async (event) => {
    if (event.key === 'Enter') {
      setLoading(true);
      
      const data = {
        cadena:searchTerm
    }
    console.log("data")
    console.log(data)
    
      try {
        const respuesta = await axios.post("http://localhost:8080/embarcaciones/buscar",data);
        setInspectors(respuesta.data.data);
        setError('');
      } catch (error) {
        console.error(error);
        setError('Error al realizar la búsqueda');
      }
      setLoading(false);
    }
  };
  const fetchDataInspectores = async () => {
    try {
      const respuesta = await axios.get("http://localhost:8080/inspectores");
      setInspectors(respuesta.data);
      setLoading(false);
    } catch (error) {
      console.error(error);
      setError('Error al cargar los datos');
      setLoading(false);
    }
  };
  useEffect(() => {
    
    fetchDataInspectores();
  }, []);

  const handleSelectOne = (index) => {
    const newInspectores = [...inspectors];
    newInspectores[index].isSelected = !newInspectores[index].isSelected;
    setInspectors(newInspectores);
  };
  const handleEnable = async () => {
  
  };
  const handleDisable = async () => {
    const selectedInspectors = inspectors.filter(inspector => inspector.isSelected);
  
    try {
      for (const inspector of selectedInspectors) {
        await axios.delete("http://localhost:8080/inspectores/eliminar", {
          data: {
            id_inspector: inspector.id_inspector
          }
        });
      }
      fetchDataInspectores(); // Asegúrate de recargar los datos para ver los cambios
    } catch (error) {
      console.error(error);
      setError(`Error al deshabilitar inspectores`);
    }
  };
  const handleEdit = async () => {
    console.log("AYUDA DIOSITO")
  };
  

  return (
    <div className="inspector-management">
      <h1>Gestión de Inspectores</h1>
      <div className="search-box">
  <input
    type="text"
    placeholder="Buscar inspectores por Nombre o DNI"
    className="search-input"
    value={searchTerm}
    onChange={handleSearchChange}
    onKeyPress={handleKeyPress}
  />
  <FaSearch className="search-icon" />
</div>
      <div className="buttons">
        <button>Crear</button>
        <button onClick={() => handleEnable()}>Habilitar</button>
        <button onClick={() => handleDisable()}>Deshabilitar</button>
      </div>
      <div className="table-container">
        <table>
          <thead>
            <tr>
            <th>
        <input
          type="checkbox"
          onChange={handleSelectAll} // Función para seleccionar/deseleccionar todos los inspectores
        />
      </th>
              <th>Nombre</th>
              <th>Apellidos</th>
              <th>DNI</th>
              <th>Correo</th>
              <th>Celular</th>
              <th>Estado</th>
              <th>Modificar</th>
            </tr>
          </thead>
          <tbody>
            {error ? (
              <tr><td colSpan="8">{error}</td></tr>
            ) : loading ? (<tr>
              <td colSpan="8" style={{ textAlign: 'center', padding: '20px' }}>
                <div className="loading-container">
                  <CircularProgress size={50} /> {/* Hace el spinner más grande */}
                  <p>Cargando...</p>
                </div>
              </td>
            </tr>
            ) : (
              inspectors.map((inspector, index) => (
                <tr key={inspector.id_inspector}>
                  <td><input type="checkbox" checked={inspector.isSelected || false} onChange={() => handleSelectOne(index)} /></td>
                  <td>{inspector.nombres}</td>
                  <td>{inspector.apellidoPaterno}</td>
                  <td>{inspector.dni}</td>
                  <td>{inspector.correo}</td>
                  <td>{inspector.celular}</td>
                  <td>
            {inspector.activo ? (
                <div className="status active">Activo</div>
            ) : (
                <div className="status inactive">Inactivo</div>
            )}
            </td>
            <td>
 
 <FaPencilAlt color="#007BFF" cursor="pointer"  onClick={() => handleEdit(inspector.id_inspector)}/> {/* Ícono azul */}

</td>
                </tr>
              ))
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default InspectorManagement;
