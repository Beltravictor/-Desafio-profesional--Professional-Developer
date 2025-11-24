import { useContext, useEffect } from "react"
import { CategoriasContext } from "../context/Categorias/CategoriasContext"
import { NavLink } from "react-router-dom"
import '../styles/CategoriaComponent.css'

export const CategoriasComponent = () => {

    const { categorias, fetchCategorias } = useContext(CategoriasContext)

    useEffect(() => {
        fetchCategorias()
    }, [])
    

    return (
        <div className='categoria'>
            {categorias.map(cat => (
                <NavLink key={cat.id} className="myLink" to={`/destinos/categoria/${cat.id}`}>
                    <div className="contenedor" id={cat.id}>
                        <div className='img-box'>
                            <img src={cat.url} alt={cat.name} />
                        </div>
                        <div className='text-box'>{cat.name}</div>
                    </div>
                </NavLink>

            ))}

        </div>
    )
}
