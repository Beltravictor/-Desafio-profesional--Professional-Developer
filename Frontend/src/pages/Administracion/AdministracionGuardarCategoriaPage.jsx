import { useContext, useEffect, useState } from 'react'
import { CategoriasContext } from "../../context/Categorias/CategoriasContext"
import '../../styles/AdministracionEditar.css'
import '../../styles/DestinosComponent.css'
import { NavLink } from 'react-router-dom'
import { HeaderComponent } from '../../components/HeaderComponent'
import { AdministracionNoResponsive } from '../../components/AdministracionNoResponsive'

export const AdministracionGuardarCategoriaPage = () => {

  const { addCategoria } = useContext(CategoriasContext)

  const [nombre, setNombre] = useState('')
  const [imagen, setImagen] = useState('')

  const [confirmacion, setConfirmacion] = useState(false)
  const [mensajeError, setMensajeError] = useState(false)
  const [mensajeErrorDescripcion, setMensajeErrorDescripcion] = useState('')

  const verificarCampos = () => {
    if (nombre && imagen) {
      guardarCategoria()
    } else {
      setMensajeErrorDescripcion('Error: Complete todos los campos')
      setMensajeError(true)
    }
  }

  const guardarCategoria = () => {
    const newCategoria = {
      name: nombre,
      url: imagen
    }
    setConfirmacion(true)
    addCategoria(newCategoria)
    setNombre('')
    setImagen('')
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
              <h2 className="form-title">Categoría Guardada Con Éxito</h2>
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
            <label>Imagen</label>
            <textarea placeholder="Ingresa la Imagen" value={imagen}
              onChange={(e) => setImagen(e.target.value)} />
          </div>

        </div>
        <div className="form-contbtn">
          <button className="form-btn" onClick={() => verificarCampos()}>Crear Categoría</button>
          <NavLink className="myLink" to={`/administracion/categorias`}>
            <button className="form-btn">Cancelar</button>
          </NavLink>
        </div>

      </div>
    </>

  )
}

