import { useContext, useEffect, useState } from "react"
import { AuthContext } from "../../../context/Usuario/AuthProvider"
import { AdministracionAutorizacion } from "../../../components/AdministracionAutorizacion"
import { AdministracionNoResponsive } from "../../../components/AdministracionNoResponsive"
import '../../../styles/AdministracionDestinosPage.css'
import { UsuarioContext } from "../../../context/Usuario/UsuarioContext"

export const AdministracionUserPage = () => {
    const { user, logout } = useContext(AuthContext)

    if (user?.role !== 'ROLE_ADMIN')
        return (<AdministracionAutorizacion />)

    const { usuarios, getUsuarios, eliminarUsuario, editarUsuario, setToken, setProfile } = useContext(UsuarioContext)

    const [usuariosFiltrados, setUsuariosFiltrados] = useState([])
    const [filtrarEmail, setFiltrarEmail] = useState('')
    const [filtrarId, setFiltrarId] = useState('')

    const [pagina, setPagina] = useState(0);
    const [confirmacionBorrar, setConfirmacionBorrar] = useState(false);
    const [elementoABorrar, setElementoABorrar] = useState();

    const elementosPorPagina = 10;
    const inicio = pagina * elementosPorPagina;
    const elementosActuales = usuariosFiltrados.slice(inicio, inicio + elementosPorPagina)
    const totalPaginas = Math.ceil(usuariosFiltrados.length / elementosPorPagina)

    useEffect(() => {
        getUsuarios(user.token)
    }, [])

    useEffect(() => {
        if (usuarios) {
            setUsuariosFiltrados(usuarios)
        }
    }, [usuarios])

    useEffect(() => {
        const filtrarUsuarios = usuarios.filter(user =>
            user.email.toLowerCase().includes(filtrarEmail.toLowerCase()) &&
            user.id.toString().includes(filtrarId)
        )
        setUsuariosFiltrados(filtrarUsuarios)
    }, [filtrarEmail, filtrarId])

    useEffect(() => {
        if (elementoABorrar != undefined) {
            setConfirmacionBorrar(true)
        }
    }, [elementoABorrar])

    useEffect(() => {
        window.scrollTo(0, 0)
    }, [pagina])

    const cancelarBorrarDestino = () => {
        setElementoABorrar()
        setConfirmacionBorrar(false)
    }

    const borrarUsuario = () => {
        eliminarUsuario(elementoABorrar.id, user.token)
        setConfirmacionBorrar(false)
        setElementoABorrar()
    }

    const toggleRole = (userElement) => {
        const newUser = structuredClone(userElement)
        if (userElement.role.slice(5) === 'USER') {
            newUser.role = 'ROLE_ADMIN'
            editarUsuario(newUser, user.token)
        }
        if (user.role.slice(5) === 'ADMIN') {
            newUser.role = 'ROLE_USER'
            editarUsuario(newUser, user.token)
            if (userElement.email === user.email) {
                logout()
                setToken(null)
                setProfile({})
            }

        }
    }

    if (!usuarios || Object.keys(usuarios).length === 0) {
        return <p>Cargando usuarios...</p>
    }

    return (
        <>
            <AdministracionNoResponsive />
            <h2 className="subtitle">Todos los Usuarios</h2>

            <div className="input-container">
                <div className="input-group">
                    <label>Email</label>
                    <input type="text" placeholder="Filtrar Por Email" value={filtrarEmail}
                        onChange={(e) => setFiltrarEmail(e.target.value)} />
                </div>

                <div className="input-group">
                    <label>Id: </label>
                    <input type="number" placeholder="Filtrar Por ID" value={filtrarId}
                        onChange={(e) => setFiltrarId(e.target.value)} />
                </div>
            </div>
            <div className="adm-pagcontenedor">
                {
                    confirmacionBorrar &&
                    <div className="adm-editar-form">
                        <div className="adm-editar-container">
                            <h2 className="adm-form-title">Seguro que quiere borrar el Usario: {elementoABorrar.email}</h2>
                            <div className="adm-botones-editar">
                                <button className="adm-form-btn" type="button" onClick={() => borrarUsuario()}>Borrar</button>
                                <button className="adm-form-btn" type="button" onClick={() => cancelarBorrarDestino()}>Cancelar</button>
                            </div>
                        </div>
                    </div>
                }

                <table className="adm-tabla-admin">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Email</th>
                            <th>Rol</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>

                    <tbody>
                        {elementosActuales.map((elemento) => (
                            <tr key={elemento.id}>
                                <td>{elemento.id}</td>
                                <td>{elemento.email}</td>
                                <td>{elemento.role.slice(5)}</td>
                                <td>
                                    <div>
                                        {elemento.role === 'ROLE_USER' &&
                                            <button className="adm-admin-btn" onClick={() => toggleRole(elemento)}>Dar Rol de Admin</button>
                                        }
                                        {elemento.role === 'ROLE_ADMIN' &&
                                            <button className="adm-admin-btn" onClick={() => toggleRole(elemento)}>Dar Rol de Usuario</button>
                                        }
                                        <button className="adm-admin-btn" onClick={() => setElementoABorrar(elemento)}>Eliminar</button>

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
        </>
    )
}