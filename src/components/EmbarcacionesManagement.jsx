
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './EmbarcacionesManagement.css'; // Asegúrate de crear este archivo CSS para estilizar el componente
import CircularProgress from '@mui/material/CircularProgress';
import { FaPencilAlt } from 'react-icons/fa';
import { FaSearch } from 'react-icons/fa';


const EmbarcacionManagement = () => {
  // Datos de ejemplo para los inspectores
  

  const [embarcaciones, setEmbarcaciones] = useState([]);
  const [searchTerm, setSearchTerm] = useState('');
  const handleSearchChange = (event) => {
    setSearchTerm(event.target.value);
  };
  const fetchDataEmbarcaciones = async () => {
    try {
      const respuesta = await axios.get("http://localhost:8080/embarcaciones");
      setEmbarcaciones(respuesta.data);
      setLoading(false);
    } catch (error) {
      console.error(error);
      setError('Error al cargar los datos');
      setLoading(false);
    }
  };
  
  useEffect(() => {
    fetchDataEmbarcaciones();
  }, []);
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
        setEmbarcaciones(respuesta.data.data);
        setError('');
      } catch (error) {
        console.error(error);
        setError('Error al realizar la búsqueda');
      }
      setLoading(false);
    }
  };
  const handleSelectAll = (e) => {
    const newEmbarcaciones= embarcaciones.map(embarcacion => ({
      ...embarcacion,
      isSelected: e.target.checked
    }));
    setEmbarcaciones(newEmbarcaciones);
  };
  
  const handleSelectOne = (index) => {
    const newEmbarcaciones = [...embarcaciones];
    newEmbarcaciones[index].isSelected = !newEmbarcaciones[index].isSelected;
    setEmbarcaciones(newEmbarcaciones);
  };
  const handleEnable = async () => {
  
  };
  const handleDisable = async () => {
    const selectedEmbarcaciones = embarcaciones.filter(embarcacion => embarcacion.isSelected);
  
    try {
      for (const embarcacion of selectedEmbarcaciones) {
        await axios.delete("http://localhost:8080/embarcaciones/eliminar", {
          data: {
            id_embarcacion: embarcacion.id_embarcacion
          }
        });
      }
      fetchDataEmbarcaciones(); // Asegúrate de recargar los datos para ver los cambios
    } catch (error) {
      console.error(error);
      setError(`Error al deshabilitar embarcaciones`);
    }
  };
  
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  
  const handleEdit = async () => {
    console.log("AYUDA DIOSITO")
  };
  

  return (
    <div className="inspector-management">
      <h1>Gestión de Embarcaciones</h1>
  

<div className="search-box">
  <input
    type="text"
    placeholder="Buscar embarcaciones por No matricula o nombre"
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
      <th>No Matricula</th>
      <th>Carga (Tn)</th>
      <th>Tripulacion</th>
      <th>Contacto</th>
      <th>Estado</th>
      <th>Modificar</th>
    </tr>
  </thead>
  <tbody>
    {error ? (
      <tr>
        <td colSpan="8">{error}</td>
      </tr>
    ) : loading ? (<tr>
        <td colSpan="8" style={{ textAlign: 'center', padding: '20px' }}>
          <div className="loading-container">
            <CircularProgress size={50} /> {/* Hace el spinner más grande */}
            <p>Cargando...</p>
          </div>
        </td>
      </tr>
    ) : (
      embarcaciones.map((embarcacion, index) => (
        <tr key={embarcacion.id_embarcacion}>
          <td>
            <input
              type="checkbox"
              checked={embarcacion.isSelected || false} // Estado de selección de cada inspector
              onChange={() => handleSelectOne(index)} // Función para manejar la selección individual
            />
          </td>
          <td>{embarcacion.nombres}</td>
          <td>{embarcacion.matricula}</td>
          <td>{embarcacion.carga}</td>
          <td>{embarcacion.tripulacion}</td>
          <td>{embarcacion.celular}</td>
                <td>
            {embarcacion.activo ? (
                <div className="status active">Activo</div>
            ) : (
                <div className="status inactive">Inactivo</div>
            )}
            </td>
            <td>
 
      <FaPencilAlt color="#007BFF" cursor="pointer"  onClick={() => handleEdit(embarcacion.id_embarcacion)}/> {/* Ícono azul */}

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

export default EmbarcacionManagement;
