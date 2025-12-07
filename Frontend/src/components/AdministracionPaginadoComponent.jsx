import { useContext, useEffect, useState } from 'react'
import '../styles/AdministracionPaginadoComponent.css'
import { NavLink } from 'react-router-dom';

export const AdministracionPaginadoComponent = ({ elementos, eliminarElemento, token, rutaEditar }) => {

    const [pagina, setPagina] = useState(0);
    const [confirmacionBorrar, setConfirmacionBorrar] = useState(false);
    const [elementoABorrar, setElementoABorrar] = useState();

    const elementosPorPagina = 10;
    const inicio = pagina * elementosPorPagina;
    const elementosActuales = elementos.slice(inicio, inicio + elementosPorPagina);
    const totalPaginas = Math.ceil(elementos.length / elementosPorPagina);

    useEffect(() => {
        setPagina(0)
    }, [elementos])

    useEffect(() => {
        if (elementoABorrar != undefined) {
            setConfirmacionBorrar(true)
        }
    }, [elementoABorrar])

    const borrarElemento = () => {
        eliminarElemento(elementoABorrar.id, token)
        setElementoABorrar()
        setConfirmacionBorrar(false)
    }
    const cancelarBorrarDestino = () => {
        setElementoABorrar()
        setConfirmacionBorrar(false)
    }

    useEffect(() => {
        window.scrollTo(0, 0)
    }, [pagina])

    return (

        <div className="adm-pagcontenedor">
            {
                confirmacionBorrar &&
                <div className="adm-editar-form">
                    <div className="adm-editar-container">
                        <h2 className="adm-form-title">Seguro que quiere borrar el Elemento: {elementoABorrar.name}</h2>
                        <div className="adm-botones-editar">
                            <button className="adm-form-btn" type="button" onClick={() => borrarElemento()}>Borrar</button>
                            <button className="adm-form-btn" type="button" onClick={() => cancelarBorrarDestino()}>Cancelar</button>
                        </div>
                    </div>
                </div>
            }

            <table className="adm-tabla-admin">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nombre</th>
                        <th>Acciones</th>
                    </tr>
                </thead>

                <tbody>
                    {elementosActuales.map((elemento) => (
                        <tr key={elemento.id}>
                            <td>{elemento.id}</td>
                            <td>{elemento.name}</td>
                            <td>
                                <div>
                                    <NavLink className="myLink" to={`${rutaEditar}/${elemento.id}`}>
                                        <button className="adm-admin-btn">Editar</button>
                                    </NavLink>
                                    <button className="adm-admin-btn" onClick={() => setElementoABorrar(elemento)}>Borrar</button>
                                </div>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>

            <div className="adm-pagcontroles">

                <button className='adm-pagbutton'
                    onClick={() => setPagina(0)}
                    disabled={pagina === 0}
                >{"<"}</button>

                <button className='adm-pagbutton'
                    onClick={() => pagina > 0 && setPagina(pagina - 1)}
                    disabled={pagina === 0}
                >Anterior</button>

                <span> {pagina + 1} / {totalPaginas} </span>

                <button className='adm-pagbutton'
                    onClick={() => pagina < totalPaginas - 1 && setPagina(pagina + 1)}
                    disabled={pagina === totalPaginas - 1}
                >Siguiente
                </button>

                <button className='adm-pagbutton'
                    onClick={() => setPagina(totalPaginas - 1)}
                    disabled={pagina === totalPaginas - 1}
                >{">"}
                </button>
            </div>
        </div>
    )
}
