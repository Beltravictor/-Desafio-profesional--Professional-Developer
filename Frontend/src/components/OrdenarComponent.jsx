import { useState } from "react";
import "../styles/OrdenarComponent.css";
import { NavLink } from "react-router-dom";

export const OrdenarComponent = ({ categorias }) => {
    const [mostrarMenu, setMostrarMenu] = useState(false);

    return (
        <div className="ordenar-contenedor">

            <h3 className="ordenar-titulo">Ordenar por</h3>

            <div className="ordenar-botones">

                <div className="ordenar-categoria-wrapper">
                    <button
                        className="ordenar-boton"
                        onClick={() => setMostrarMenu(!mostrarMenu)}
                    >Categoría ▾</button>

                    {mostrarMenu && (
                        <div className="ordenar-menu">
                            {categorias.map(c => (
                                <NavLink className="myLink" key={c.id} to={`/destinos/categoria/${c.id}`}>
                                    <button
                                        key={c.id}
                                        className="ordenar-menu-item"
                                        onClick={() => {
                                            setMostrarMenu(false);
                                        }}
                                    >{c.name}</button>
                                </NavLink>

                            ))}
                        </div>
                    )}
                </div>

                <NavLink className="myLink" to={`/destinos/nombre`}>
                    <button className="ordenar-boton">Nombre</button>
                </NavLink>

                <NavLink className="myLink" to={`/destinos/precio`}>
                    <button className="ordenar-boton">Precio</button>
                </NavLink>

                <NavLink className="myLink" to={`/destinos/rating`}>
                    <button className="ordenar-boton">Rating</button>
                </NavLink>

                <NavLink className="myLink" to={`/destinos`}>
                    <button className="ordenar-boton">Todos los Destinos</button>
                </NavLink>

            </div>
        </div>
    );
};
