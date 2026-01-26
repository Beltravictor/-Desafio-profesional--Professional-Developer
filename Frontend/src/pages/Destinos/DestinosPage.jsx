import { useContext, useEffect, useState } from "react"
import { DestinosContext } from "../../context/Destinos/DestinosContext"
import { CategoriasContext } from "../../context/Categorias/CategoriasContext"
import { PaginadoComponent } from "../../components/PaginadoComponent"
import { OrdenarComponent } from "../../components/OrdenarComponent"
import { useSearchParams } from "react-router-dom"

export const DestinosPage = () => {

    const [params] = useSearchParams()
    const { destinos, fetchDestinos } = useContext(DestinosContext)
    const { categorias, fetchCategorias } = useContext(CategoriasContext)

    const [filtrados, setFiltrados] = useState([])
    const [categoriasFiltro, setCategoriasFiltro] = useState([])
    const [orden, setOrden] = useState('')
    const [title, setTitle] = useState('')
    const [pagina, setPagina] = useState(0);

    useEffect(() => {
        fetchDestinos()
        fetchCategorias()
    }, [])

    useEffect(() => {
        setCategoriasFiltro(params.get("categorias") ? params.get("categorias") != '' && params.get("categorias").split(',').map(Number) : [])
        setOrden(params.get("orden") || '')
        setPagina(Number(params.get("pagina")) != 0 ? Number(params.get("pagina")) : 0)
    }, [])

    useEffect(() => {
        if (!destinos || destinos.length === 0) {
            return
        }

        let resultado = [...destinos]

        if (categoriasFiltro.length > 0) {
            resultado = resultado.filter(des =>
                categoriasFiltro.every(catFiltro =>
                    des.categories.some(cat => cat === catFiltro)
                )
            )
        }

        switch (orden) {
            case 'nombre':
                resultado.sort((a, b) => a.name.localeCompare(b.name))
                setTitle('Ordenados Por Nombre')
                break

            case 'precio':
                resultado.sort((a, b) => b.sample_price - a.sample_price)
                setTitle('Ordenados Por Precio')
                break

            case 'rating':
                resultado.sort((a, b) => b.rating - a.rating)
                setTitle('Ordenados Por Rating')
                break

            default:
                setTitle('Todos Nuestros Destinos')
                break
        }

        setFiltrados(resultado)

    }, [destinos, categoriasFiltro, orden])


    if (!destinos || Object.keys(destinos).length === 0
        || !categorias || Object.keys(categorias).length === 0) {
        return <p>Cargando destinos...</p>
    }

    return (
        <>
            <h2 className="destino-title">Destinos encontrdos: {filtrados.length}</h2>
            <h2 className="destino-subtitle">{title}</h2>
            <OrdenarComponent
                elements={categorias} elementsFilter={categoriasFiltro} setElementsFilter={setCategoriasFiltro}
                setOrden={setOrden}
            />

            {filtrados.length === 0 ? (
                <h2 className="subtitle">No se encontraron destinos.</h2>
            ) : (
                <PaginadoComponent destinos={filtrados} pagina={pagina} setPagina={setPagina} />
            )}

            <div style={{ height: "20px" }} />
        </>

    )
}
