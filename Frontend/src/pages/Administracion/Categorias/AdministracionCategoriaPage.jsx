import { useContext, useEffect, useState } from 'react'
import { CategoriasContext } from '../../../context/Categorias/CategoriasContext'
import { NavLink } from 'react-router-dom'
import { AdministracionPaginadoComponent } from '../../../components/AdministracionPaginadoComponent'
import '../../../styles/AdministracionDestinosPage.css'
import { AdministracionNoResponsive } from '../../../components/AdministracionNoResponsive'
import { AuthContext } from '../../../context/Usuario/AuthProvider'
import { AdministracionAutorizacion } from '../../../components/AdministracionAutorizacion'

export const AdministracionCategoriaPage = () => {
    const { user } = useContext(AuthContext)

    if (user?.role !== 'ROLE_ADMIN')
        return (<AdministracionAutorizacion />)

    const { categorias, fetchCategorias, eliminarCategoria } = useContext(CategoriasContext)

    const [categoriasFiltradas, setCategoriasFiltradas] = useState([])
    const [filtrarNombre, setFiltrarNombre] = useState('')
    const [filtrarId, setFiltrarId] = useState('')

    useEffect(() => {
        fetchCategorias()
    }, [])

    useEffect(() => {
        if (categorias)
            setCategoriasFiltradas(categorias)
    }, [categorias])

    useEffect(() => {
        setCategoriasFiltradas(
            categorias.filter(categoria =>
                categoria.name.toLowerCase().includes(filtrarNombre.toLowerCase()) &&
                categoria.id.toString().includes(filtrarId)
            )
        )
    }, [filtrarNombre, filtrarId])


    if (!categorias || Object.keys(categorias).length === 0) {
        return <p>Cargando categorias...</p>
    }

    return (
        <>
            <AdministracionNoResponsive />
            <h2 className="subtitle">Todas Las Categorías</h2>
            <div className="admin-crear-categoria">
                <NavLink className="myLink" to={`/administracion/categorias/crear`}>
                    <button className="admin-btn">Crear Categoría</button>
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
                elementos={categoriasFiltradas}
                eliminarElemento={eliminarCategoria}
                token={user.token}
                rutaEditar={"/administracion/categorias/editar"} />
        </>
    )
}