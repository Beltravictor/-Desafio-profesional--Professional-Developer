import { useContext, useEffect, useState } from "react"
import { DestinosContext } from "../../context/Destinos/DestinosContext"
import { useParams } from "react-router-dom"
import { CategoriasContext } from "../../context/Categorias/CategoriasContext"
import { PaginadoComponent } from "../../components/PaginadoComponent"
import { OrdenarComponent } from "../../components/OrdenarComponent"

export const DestinosPage = () => {

    const { filtro, id } = useParams()
    const { destinos, fetchDestinos } = useContext(DestinosContext)
    const { categorias, fetchCategorias } = useContext(CategoriasContext)

    const [filtrados, setFiltrados] = useState([])
    const [title, setTitle] = useState('')

    const filtrar = () => {
        switch (filtro) {
            case 'categoria':
                setFiltrados(
                    destinos.filter(destino =>
                        destino.categories.includes(Number(id))
                    )
                )
                setTitle(`Resultados Por Categoría: ${categorias[id - 1]?.name}`)
                break
            case 'nombre':
                setFiltrados(
                    destinos.sort((a, b) =>
                        a.name.localeCompare(b.name)
                    ))
                setTitle('Resultados Odernados Alafbeticamente')
                break
            case 'precio':
                setFiltrados(
                    destinos.sort((a, b) =>
                        b.sample_price - a.sample_price
                    ))
                setTitle('Resultados Odernados Por Precio')
                break
            case 'rating':
                setFiltrados(
                    destinos.sort((a, b) =>
                        b.rating - a.rating
                    ))
                setTitle('Resultados Odernados Por Rating')
                break
            default:
                setFiltrados(destinos.sort((a, b) =>
                    a.id - b.id
                ))
                setTitle(`Todos Nuestros Destinos`)
                break
        }
    }

    useEffect(() => {
        fetchDestinos()
        fetchCategorias()
    }, [])

    useEffect(() => {
        if (destinos.length > 0) {
            filtrar();
        }
    }, [destinos, filtro, id])

    if (!destinos || Object.keys(destinos).length === 0
        || !categorias || Object.keys(categorias).length === 0) {
        return <p>Cargando destinos...</p>
    }

    return (
        <>
            <h2 className="subtitle">{title}</h2>
            <OrdenarComponent
                categorias={categorias}
            />

            {filtrados.length === 0 ? (
                <h2 className="subtitle">No se encontraron destinos.</h2>
            ) : (
                <PaginadoComponent destinos={filtrados} />
            )}
        </>

    )
}
