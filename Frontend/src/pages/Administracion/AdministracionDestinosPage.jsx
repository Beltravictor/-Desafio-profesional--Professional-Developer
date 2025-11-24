import { useContext, useEffect, useState } from "react"
import { DestinosContext } from "../../context/Destinos/DestinosContext"
import { NavLink } from "react-router-dom"
import { AdministracionPaginadoComponent } from "../../components/AdministracionPaginadoComponent"
import '../../styles/AdministracionDestinosPage.css'
import { AdministracionNoResponsive } from "../../components/AdministracionNoResponsive"

export const AdministracionDestinosPage = () => {

    const { destinos, fetchDestinos, eliminarDestinos } = useContext(DestinosContext)

    const [destinosFiltrados, setDestinosFiltrados] = useState([])
    const [filtrarNombre, setFiltrarNombre] = useState('')
    const [filtrarId, setFiltrarId] = useState('')

    useEffect(() => {
        fetchDestinos()
    }, [])

    useEffect(() => {
        if (destinos)
            setDestinosFiltrados(destinos)
    }, [destinos])

    useEffect(() => {
        setDestinosFiltrados(
            destinos.filter(destino =>
                destino.name.toLowerCase().includes(filtrarNombre.toLowerCase()) &&
                destino.id.toString().includes(filtrarId)
            )
        )
    }, [filtrarNombre, filtrarId])

    if (!destinos || Object.keys(destinos).length === 0) {
        return <p>Cargando destinos...</p>
    }

    return (
        <>
            <AdministracionNoResponsive />
            <h2 className="subtitle">Todos los Destinos</h2>
            <div className="admin-crear-destino">
                <NavLink className="myLink" to={`/administracion/destinos/crear`}>
                    <button className="admin-btn">Crear Destino</button>
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
                elementos={destinosFiltrados}
                eliminarElemento={eliminarDestinos}
                rutaEditar={"/administracion/destinos/editar"} />
        </>
    )
}
