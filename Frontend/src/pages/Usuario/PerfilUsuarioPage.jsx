import { useContext, useEffect, useState } from 'react'
import { AuthContext } from '../../context/Usuario/AuthProvider'
import '../../styles/PerfilUsuarioPage.css'
import { UsuarioContext } from '../../context/Usuario/UsuarioContext'
import { NavLink } from 'react-router-dom'
import { AvatarPerfilComponent } from '../../components/AvatarPerfilComponent'

export const PerfilUsuarioPage = () => {

    const { user, logout } = useContext(AuthContext)
    const { profile, setProfile, getProfile, setToken, eliminarMiUsuario } = useContext(UsuarioContext)
    const [confirmacionBorrar, setConfirmacionBorrar] = useState(false)

    const cerrarSesion = () => {
        logout()
        setToken(null)
        setProfile({})
    }

    useEffect(() => {
        if (user) {
            getProfile(user.token)
        }
    }, [user])

    const eliminarCuenta = () => {
        eliminarMiUsuario(user.token)
        cerrarSesion()
    }


    if (!user) {
        return (
            <div className="profile-container">
                <div className="profile-card">
                    <h2 className="profile-name">Para ver tu Perfil Inicia Sesión</h2>
                    <NavLink className="myLink" to='/registro'>
                        <button className="edit-button">Iniciar Sesión</button>
                    </NavLink>
                    <NavLink className="myLink" to='/login'>
                        <button className="edit-button">¿Todavía no tenes una cuenta? Registrate!</button>
                    </NavLink>
                </div>
            </div>
        )
    }


    if (!profile || Object.keys(profile).length === 0) {
        return (
            <div className="profile-container">
                <h2 className="profile-name">Cargando Perfil...</h2>
            </div>
        )
    }

    return (
        <div className="profile-container">
            {
                confirmacionBorrar &&
                <div className="adm-editar-form">
                    <div className="adm-editar-container">
                        <h2 className="adm-form-title">¿Seguro que quiere eliminar su Cuenta?</h2>
                        <div className="adm-botones-editar">
                            <NavLink to='/' className='logo myLink'>
                                <button className="adm-form-btn" type="button" onClick={() => eliminarCuenta()}>Eliminar</button>
                            </NavLink>
                            <button className="adm-form-btn" type="button" onClick={() => setConfirmacionBorrar(false)}>Cancelar</button>
                        </div>
                    </div>
                </div>
            }
            <div className="profile-card">
                <AvatarPerfilComponent className="profile-avatar" />
                <h2 className="profile-name">{profile.firstname} {profile.lastname}</h2>
                <p className="profile-email">{profile.email}</p>

                <div className="profile-info">
                    <h3>Información</h3>
                    <p><strong>País:</strong> Argentina</p>
                    <p><strong>Miembro desde: </strong>{profile.creationDate?profile.creationDate.slice(0,10): 'admin'}</p>
                    <p><strong>Rol: </strong>{profile?.role.slice(5)}</p>
                </div>

                <NavLink to='/' className='logo myLink'>
                    <button className='edit-button' onClick={() => cerrarSesion()}>Cerrar Sesión</button>
                </NavLink>
                <button className='delete-button' onClick={() => setConfirmacionBorrar(true)}>Eliminar Cuenta</button>
            </div>
        </div>
    )
}
