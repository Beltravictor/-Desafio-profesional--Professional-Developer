import { useContext, useEffect, useState } from 'react'
import { NavLink } from 'react-router-dom'
import { AdministracionPaginadoComponent } from '../../../components/AdministracionPaginadoComponent'
import '../../../styles/AdministracionDestinosPage.css'
import { AdministracionNoResponsive } from '../../../components/AdministracionNoResponsive'
import { AuthContext } from '../../../context/Usuario/AuthProvider'
import { AdministracionAutorizacion } from '../../../components/AdministracionAutorizacion'
import { CaracteristicasContext } from '../../../context/Caracteristicas/CaracteristicasContext'

export const AdministracionCaracteristicasPage = () => {
    const { user } = useContext(AuthContext)

    if (user?.role !== 'ROLE_ADMIN')
        return (<AdministracionAutorizacion />)

    const {caracteristicas, fetchCaracteristicas, eliminarCaracteristicas} = useContext(CaracteristicasContext)

    const [caracteristicasFiltradas, setCaracteristicasFiltradas] = useState([])
    const [filtrarNombre, setFiltrarNombre] = useState('')
    const [filtrarId, setFiltrarId] = useState('')

    useEffect(() => {
        fetchCaracteristicas()
    }, [])

    useEffect(() => {
        if (caracteristicas)
            setCaracteristicasFiltradas(caracteristicas)
    }, [caracteristicas])

    useEffect(() => {
        setCaracteristicasFiltradas(
            caracteristicas.filter(categoria =>
                categoria.name.toLowerCase().includes(filtrarNombre.toLowerCase()) &&
                categoria.id.toString().includes(filtrarId)
            )
        )
    }, [filtrarNombre, filtrarId])


    if (!caracteristicas || Object.keys(caracteristicas).length === 0) {
        return <p>Cargando caracteristicas...</p>
    }
    
    return (
        <>
            <AdministracionNoResponsive />
            <h2 className="subtitle">Todas Las Características</h2>
            <div className="admin-crear-categoria">
                <NavLink className="myLink" to={`/administracion/caracteristicas/crear`}>
                    <button className="admin-btn">Crear Característica</button>
                </NavLink>
            </div>

            <div className="input-container">
                <div className="input-group">
                    <label>Nombre</label>
                    <input type="text" placeholder="Filtrar Por Nombre" value={filtrarNombre}
                        onChange={(e) => setFiltrarNombre(e.target.value)} />
                </div>

                <div className="input-group">
                    <label>Id: </label>
                    <input type="number" placeholder="Filtrar Por ID" value={filtrarId}
                        onChange={(e) => setFiltrarId(e.target.value)} />
                </div>
            </div>
            <AdministracionPaginadoComponent
                elementos={caracteristicasFiltradas}
                eliminarElemento={eliminarCaracteristicas}
                token={user.token}
                rutaEditar={"/administracion/caracteristicas/editar"} />
        </>
    )
}
