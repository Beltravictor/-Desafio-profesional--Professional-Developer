import { useEffect, useState } from 'react'
import '../styles/PaginadoComponent.css'
import { RecomendacionesComponent } from './RecomendacionesComponent';

export const PaginadoComponent = ({ destinos, pagina, setPagina }) => {
    
    const destinosPorPagina = 10;
    const inicio = pagina * destinosPorPagina;
    const destinosActuales = destinos.slice(inicio, inicio + destinosPorPagina);
    const totalPaginas = Math.ceil(destinos.length / destinosPorPagina);

    useEffect(() => {
        window.scrollTo(0, 0)
    }, [pagina])

    return (
        <div className="pagcontenedor">
            <div className="paggrid">
                {destinosActuales.map((destino) => (
                    <RecomendacionesComponent destino={destino} key={destino.id} />
                ))}
            </div>

            <div className="pagcontroles">

                <button className='pagbutton'
                    onClick={() => setPagina(0)}
                    disabled={pagina === 0}
                >{"<"}</button>

                <button className='pagbutton'
                    onClick={() => pagina > 0 && setPagina(pagina - 1)}
                    disabled={pagina === 0}
                >Anterior</button>

                <span> {pagina + 1} / {totalPaginas} </span>

                <button className='pagbutton'
                    onClick={() => pagina < totalPaginas - 1 && setPagina(pagina + 1)}
                    disabled={pagina === totalPaginas - 1}
                >Siguiente
                </button>

                <button className='pagbutton'
                    onClick={() => setPagina(totalPaginas - 1)}
                    disabled={pagina === totalPaginas - 1}
                >{">"}
                </button>
            </div>
        </div>
    )
}
