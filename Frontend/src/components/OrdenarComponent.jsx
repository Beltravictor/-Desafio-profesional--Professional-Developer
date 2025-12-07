import { useState } from "react"
import "../styles/OrdenarComponent.css"

export const OrdenarComponent = ({ elements, elementsFilter, setElementsFilter, setOrden }) => {
    const [mostrarMenu, setMostrarMenu] = useState(false)
    const [mostrarOrden, setMostrarOrden] = useState(false)

    return (
        <div className="ordenar-contenedor">

            <div className="ordenar-botones">

                <div className="ordenar-categoria-wrapper">
                    <button
                        className="ordenar-boton"
                        onClick={() => {
                            setMostrarMenu(!mostrarMenu)
                        }}
                    >Filtrar por Categoría ▾</button>

                    {mostrarMenu && (
                        <div className="ordenar-menu">
                            {elements.filter(el => !elementsFilter.includes(el.id))
                                .map(c => (
                                    <button
                                        key={c.id}
                                        className="ordenar-menu-item"
                                        onClick={() => {
                                            setMostrarMenu(false)
                                            setMostrarOrden(false)
                                            setElementsFilter([...elementsFilter, c.id])
                                        }}
                                    >{c.name}</button>
                                ))}
                        </div>
                    )}
                </div>

                <div className="ordenar-categoria-wrapper">
                    <button
                        className="ordenar-boton"
                        onClick={() => {
                            setMostrarOrden(!mostrarOrden)
                        }}
                    >Ordenar Por ▾</button>

                    {mostrarOrden && (
                        <div className="ordenar-menu">
                            <button className="ordenar-menu-item"
                                onClick={() => {
                                    setMostrarMenu(false)
                                    setMostrarOrden(false)
                                    setOrden('nombre')
                                }}
                            >Nombre</button>

                            <button className="ordenar-menu-item"
                                onClick={() => {
                                    setMostrarMenu(false)
                                    setMostrarOrden(false)
                                    setOrden('precio')
                                }}
                            >Precio</button>

                            <button className="ordenar-menu-item"
                                onClick={() => {
                                    setMostrarMenu(false)
                                    setMostrarOrden(false)
                                    setOrden('rating')
                                }}
                            >Rating</button>
                        </div>
                    )}
                </div>

            </div>
            <div className="ordenar-filtros-activos">
                {elementsFilter.map((cat) => (
                    <div key={cat} className="filtro-item">
                        <span>{elements.find(cate => cate.id === cat).name}</span>
                        <button
                            className="filtro-btn-eliminar"
                            onClick={() =>
                                setElementsFilter(elementsFilter.filter(cate => cate !== cat))
                            }
                        >
                            X
                        </button>
                    </div>
                ))}
            </div>
        </div>
    )
}
