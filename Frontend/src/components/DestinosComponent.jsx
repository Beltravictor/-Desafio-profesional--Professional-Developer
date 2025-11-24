import { DestinosContext } from '../context/Destinos/DestinosContext';
import '../styles/DestinosComponent.css'
import { useContext, useState } from "react";


export const DestinosComponent = ({ destino }) => {

    const { eliminarDestinos, editarDestinos } = useContext(DestinosContext)

    const admin = true;

    const [editar, setEditar] = useState(false)

    const [nombre, setNombre] = useState(destino.name)
    const [precio, setPrecio] = useState(destino.sample_price)
    const [imagenes, setImagenes] = useState(destino.images)
    const [addImagenes, setAddImagenes] = useState('')
    const [categoria, setCategoria] = useState(destino.categories)
    const [addCategoria, setAddCategoria] = useState('')
    const [descripcion, setDescripcion] = useState(destino.description)

    const actualizarImagen = (i, valor) => {
        setImagenes(imgs => {
            const copia = [...imgs];
            copia[i] = valor;
            return copia;
        });
    };

    const actualizarCategoria = (i, valor) => {
        setCategoria(cats => {
            const copia = [...cats];
            copia[i] = valor;
            return copia;
        });
    };

    const guardarDestinos = () => {

        let imagensFinales = imagenes.filter(img => img !== '')
        if(addImagenes != ''){
            imagensFinales = [...imagensFinales, addImagenes]
            setAddImagenes('')
        }

        setImagenes(imagensFinales)
        
        let categoriasFinales = categoria.filter(cat => cat !== '')
        
        if (addCategoria != '') {
            categoriasFinales = [...categoriasFinales, addCategoria]
            setAddCategoria('')
        }

        setCategoria(categoriasFinales)

        const newDestino = {
            id: destino.id,
            name: nombre,
            sample_price: precio,
            images: imagensFinales,
            categories: categoriasFinales,
            description: descripcion
        }
        editarDestinos(newDestino)
        setEditar(false)
    }

    const cancelarDestinos = () => {
        setEditar(false)
        setNombre(destino.name)
        setPrecio(destino.sample_price)
        setImagenes(destino.images)
        setAddImagenes('')
        setCategoria(destino.categories)
        setAddCategoria('')
        setDescripcion(destino.description)
    }


    return (
        <>
            <div>

                {admin && (
                    <div className="botones-vertical">
                        <button onClick={() => setEditar(true)} >Editar</button>
                        <button onClick={() => eliminarDestinos(destino.id)}>Borrar</button>
                    </div>)}

                {editar && (
                    <div className="editar-form">
                        <div className="editar-container">
                            <h2 className="form-title">Editar elemento</h2>

                            <form >
                                <div className="campo">
                                    <label>Nombre: </label>
                                    <input type="text" value={nombre}
                                        onChange={(e) => setNombre(e.target.value)} />
                                </div>

                                <div className="campo">
                                    <label>Precio: </label>
                                    <input type="number" value={precio}
                                        onChange={(e) => setPrecio(e.target.value)} />
                                </div>

                                <div className="campo">
                                    <label>Imagenes: </label>
                                    <div>
                                        {imagenes.map((img, index) => (
                                            <input type="text" value={img}
                                                onChange={(e) => actualizarImagen(index, e.target.value)}
                                            />
                                        ))}
                                    </div>
                                </div>
                                <div className="campo">
                                    <label>Anadir Imagen: </label>
                                    <input type="text"
                                        onChange={(e) => setAddImagenes(e.target.value)} />
                                </div>

                                <div className="campo">
                                    <label>Categorias: </label>
                                    <div>
                                        {categoria.map((cat, index) => (
                                            <input type="number" value={cat}
                                                onChange={(e) => actualizarCategoria(index, e.target.value)}
                                            />
                                        ))}
                                    </div>
                                </div>
                                <div className="campo">
                                    <label>Anadir Categoria: </label>
                                    <input type="number"
                                        onChange={(e) => setAddCategoria(e.target.value)} />
                                </div>

                                <div className="campo">
                                    <label>Descripción: </label>
                                    <input type="text" value={descripcion}
                                        onChange={(e) => setDescripcion(e.target.value)} />
                                </div>

                                <div className="botones-editar">
                                    <button type="button" onClick={() => guardarDestinos()}>Guardar</button>
                                    <button type="button" onClick={() => cancelarDestinos()}>Cancelar</button>
                                </div>
                            </form>
                        </div>
                    </div>
                )}
            </div>

        </>

    );
}

