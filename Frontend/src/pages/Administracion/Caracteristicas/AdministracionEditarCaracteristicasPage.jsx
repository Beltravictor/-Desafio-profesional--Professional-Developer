import { useContext, useEffect, useState } from 'react'
import { NavLink, useParams } from 'react-router-dom'
import { HeaderComponent } from '../../../components/HeaderComponent'
import { AdministracionNoResponsive } from '../../../components/AdministracionNoResponsive'
import { AuthContext } from '../../../context/Usuario/AuthProvider'
import { AdministracionAutorizacion } from '../../../components/AdministracionAutorizacion'
import { CaracteristicasContext } from '../../../context/Caracteristicas/CaracteristicasContext'

export const AdministracionEditarCaracteristicasPage = () => {
  const { user } = useContext(AuthContext)

  if (user?.role !== 'ROLE_ADMIN')
    return (<AdministracionAutorizacion />)

  const { id } = useParams()
  const { editarCaracteristica, caracteristicaPorID, buscarCaracteristicaPorId } = useContext(CaracteristicasContext)

  const [nombre, setNombre] = useState('')
  const [address, serAddress] = useState('')

  const [confirmacion, setConfirmacion] = useState(false)

  useEffect(() => {
    buscarCaracteristicaPorId(id)
  }, [])

  useEffect(() => {
    if (caracteristicaPorID) {
      setNombre(caracteristicaPorID.name ?? "")
      serAddress(caracteristicaPorID.address ?? "")
    }
  }, [caracteristicaPorID])

  const enviarCaracteristica = () => {
    const newCaracteristica = {
      id: id,
      name: nombre,
      address: address
    }
    setConfirmacion(true)
    editarCaracteristica(newCaracteristica, user.token)
  }

  if (!caracteristicaPorID || Object.keys(caracteristicaPorID).length === 0) {
    return <p>Cargando característica...</p>
  }

  return (
    <>
      <AdministracionNoResponsive />
      <HeaderComponent name={caracteristicaPorID.name} />

      <div className="form-contenedor">

        {
          confirmacion &&
          <div className="editar-form">
            <div className="editar-container">
              <h2 className="form-title">Característica Editada Con Éxito</h2>
              <div className="botones-editar">
                <NavLink className="myLink" to={`/administracion/caracteristicas`}>
                  <button type="button" onClick={() => setConfirmacion(false)}>Guardar</button>
                </NavLink>
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
              onChange={(e) => serAddress(e.target.value)} />
          </div>

        </div>
        <div className="form-contbtn">
          <button className="form-btn" onClick={() => enviarCaracteristica()}>Editar Categoría</button>
          <NavLink className="myLink" to={`/administracion/caracteristicas`}>
            <button className="form-btn">Cancelar</button>
          </NavLink>
        </div>

      </div>
    </>

  )
}

