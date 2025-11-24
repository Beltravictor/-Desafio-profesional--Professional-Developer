import { useContext, useEffect, useState } from 'react'
import { CategoriasContext } from "../../context/Categorias/CategoriasContext"
import '../../styles/AdministracionEditar.css'
import '../../styles/DestinosComponent.css'
import { DestinosContext } from '../../context/Destinos/DestinosContext'
import { NavLink, useParams } from 'react-router-dom'
import { HeaderComponent } from '../../components/HeaderComponent'
import { AdministracionNoResponsive } from '../../components/AdministracionNoResponsive'

export const AdministracionGuardarCategoriaPage = () => {

  const { id } = useParams()

  const { addCategoria } = useContext(CategoriasContext)

  const [nombre, setNombre] = useState('')
  const [imagen, setImagen] = useState('')

  const [confirmacion, setConfirmacion] = useState(false)

  const enviarDestino = () => {
    const newCategoria = {
      name: nombre,
      url: imagen
    }
    setConfirmacion(true)
    addCategoria(newCategoria)
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
          <button className="form-btn" onClick={() => enviarDestino()}>Crear Categoría</button>
          <NavLink className="myLink" to={`/administracion/categorias`}>
            <button className="form-btn">Cancelar</button>
          </NavLink>
        </div>

      </div>
    </>

  )
}

