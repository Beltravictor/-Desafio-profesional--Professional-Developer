import { useContext, useEffect, useState } from 'react'
import { NavLink, useParams } from 'react-router-dom'
import { CategoriasContext } from '../../../context/Categorias/CategoriasContext'
import { HeaderComponent } from '../../../components/HeaderComponent'
import { AdministracionNoResponsive } from '../../../components/AdministracionNoResponsive'
import { AuthContext } from '../../../context/Usuario/AuthProvider'
import { AdministracionAutorizacion } from '../../../components/AdministracionAutorizacion'

export const AdministracionEditarCategoriaPage = () => {

  const { user } = useContext(AuthContext)

  if (user?.role !== 'ROLE_ADMIN')
    return (<AdministracionAutorizacion />)

  const { id } = useParams()

  const { categoriaPorID, buscarCategoriaPorId, editarCategoria } = useContext(CategoriasContext)

  const [nombre, setNombre] = useState('')
  const [imagen, setImagen] = useState('')

  const [confirmacion, setConfirmacion] = useState(false)

  useEffect(() => {
    buscarCategoriaPorId(id)
  }, [])

  useEffect(() => {
    if (categoriaPorID) {
      setNombre(categoriaPorID.name ?? "")
      setImagen(categoriaPorID.url ?? "")
    }
  }, [categoriaPorID])

  const enviarCategoria = () => {
    const newCategoria = {
      id: id,
      name: nombre,
      url: imagen
    }
    setConfirmacion(true)
    editarCategoria(newCategoria, user.token)
  }

  if (!categoriaPorID || Object.keys(categoriaPorID).length === 0) {
    return <p>Cargando categoría...</p>
  }

  return (
    <>
      <AdministracionNoResponsive />
      <HeaderComponent name={categoriaPorID.name} />

      <div className="form-contenedor">

        {
          confirmacion &&
          <div className="editar-form">
            <div className="editar-container">
              <h2 className="form-title">Categoría Editada Con Éxito</h2>
              <div className="botones-editar">
                <button type="button" onClick={() => setConfirmacion(false)}>Guardar</button>
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
          <button className="form-btn" onClick={() => enviarCategoria()}>Editar Categoría</button>
          <NavLink className="myLink" to={`/administracion/categorias`}>
            <button className="form-btn">Cancelar</button>
          </NavLink>
        </div>

      </div>
    </>

  )
}
