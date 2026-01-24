import { useContext, useEffect, useState } from 'react'
import '../../../styles/AdministracionEditar.css'
import '../../../styles/DestinosComponent.css'
import { NavLink } from 'react-router-dom'
import { HeaderComponent } from '../../../components/HeaderComponent'
import { AdministracionNoResponsive } from '../../../components/AdministracionNoResponsive'
import { AuthContext } from '../../../context/Usuario/AuthProvider'
import { AdministracionAutorizacion } from '../../../components/AdministracionAutorizacion'
import { CaracteristicasContext } from '../../../context/Caracteristicas/CaracteristicasContext'

export const AdministracionGuardarCaracteristicasPage = () => {
  const { user } = useContext(AuthContext)

  if (user?.role !== 'ROLE_ADMIN')
    return (<AdministracionAutorizacion />)

  const { addCaracteristicas } = useContext(CaracteristicasContext)

  const [nombre, setNombre] = useState('')
  const [address, setAddress] = useState('')

  const [confirmacion, setConfirmacion] = useState(false)
  const [mensajeError, setMensajeError] = useState(false)
  const [mensajeErrorDescripcion, setMensajeErrorDescripcion] = useState('')

  const verificarCampos = () => {
    if (nombre && address) {
      guardarCaracteristica()
    } else {
      setMensajeErrorDescripcion('Error: Complete todos los campos')
      setMensajeError(true)
    }
  }

  const guardarCaracteristica = () => {
    const newCaracteristica = {
      name: nombre,
      address: address
    }
    setConfirmacion(true)
    addCaracteristicas(newCaracteristica, user.token)
    setNombre('')
    setAddress('')
  }

  return (
    <>
      <AdministracionNoResponsive />
      <HeaderComponent />

      <div className="form-contenedor">

        {
          confirmacion &&
          <div className="editar-form">
            <div className="editar-container">
              <h2 className="form-title">Característica Guardada Con Éxito</h2>
              <div className="botones-editar">
                <button type="button" onClick={() => setConfirmacion(false)}>Ok</button>
              </div>
            </div>
          </div>
        }

        {
          mensajeError &&
          <div className="editar-form">
            <div className="editar-container">
              <h2 className="form-title">{mensajeErrorDescripcion}</h2>
              <div className="botones-editar">
                <button className="form-btn" type="button" onClick={() => setMensajeError(false)}>Ok</button>
              </div>
            </div>
          </div>
        }


        <div className="form-grid">

          <div className="form-item">
            <label>Nombre</label>
            <input type="text" placeholder="Ingresa el nombre" value={nombre}
              onChange={(e) => setNombre(e.target.value)} />
          </div>

          <div className="form-item form-descripcion">
            <label>Dirección</label>
            <textarea placeholder="Ingresa la Dirección" value={address}
              onChange={(e) => setAddress(e.target.value)} />
          </div>

        </div>
        <div className="form-contbtn">
          <button className="form-btn" onClick={() => verificarCampos()}>Crear Característica</button>
          <NavLink className="myLink" to={`/administracion/caracteristicas`}>
            <button className="form-btn">Cancelar</button>
          </NavLink>
        </div>

      </div>
    </>

  )
}
