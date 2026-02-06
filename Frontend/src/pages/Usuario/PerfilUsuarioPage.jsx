import { useContext, useEffect, useState } from 'react'
import { NavLink } from 'react-router-dom'

import { AuthContext } from '../../context/Usuario/AuthProvider'
import { UsuarioContext } from '../../context/Usuario/UsuarioContext'
import { MyUserContext } from '../../context/MyUser/MyUserContext'

import { AvatarPerfilComponent } from '../../components/AvatarPerfilComponent'
import { DestinoFavoritoComponent } from '../../components/DestinoFavoritoComponent'
import { ReservaUserComponent } from '../../components/ReservaUserComponent'
import { MisReviewsComponent } from '../../components/MisReviewsComponent'

import '../../styles/PerfilUsuarioPage.css'

const EmptyState = ({ text }) => (
    <div className="empty-state">
        <p>{text}</p>
    </div>
)

export const PerfilUsuarioPage = () => {
    const { user, logout } = useContext(AuthContext)

    const {
        misReservas,
        verMiReservas,
        eliminarMiReservaPorId,
        destinosFavoritos,
        getDestinosFavoritos,
        misReviews,
        getMisReviews,
        eliminarMiReviewPorId
    } = useContext(MyUserContext)

    const {
        profile,
        setProfile,
        getProfile,
        setToken,
        eliminarMiUsuario,
        addFavorite,
        removeFavorite
    } = useContext(UsuarioContext)

    const [confirmacionBorrar, setConfirmacionBorrar] = useState(false)
    const [activeSection, setActiveSection] = useState('favoritos')


    useEffect(() => {
        if (user) getProfile(user.token)
    }, [user])

    useEffect(() => {
        if (user && profile) {
            getDestinosFavoritos(user.token)
            verMiReservas(user.token)
            getMisReviews(user.token)
        }
    }, [profile])


    const cerrarSesion = () => {
        logout()
        setToken(null)
        setProfile({})
    }

    const eliminarCuenta = () => {
        eliminarMiUsuario(user.token)
        cerrarSesion()
    }

    const cancelarReserva = async (id) => {
        await eliminarMiReservaPorId(user.token, id)
        await verMiReservas(user.token)
    }

    const borrarReview = async (id) => {
        await eliminarMiReviewPorId(user.token, id)
        await getMisReviews(user.token)
    }

    const actualizarReviews = async () => {
        await getMisReviews(user.token)
    }


    if (!user) {
        return (
            <div className="profile-container">
                <div className="profile-card">
                    <h2>Para ver tu perfil iniciá sesión</h2>

                    <NavLink to="/login" className="myLink">
                        <button className="edit-button">Iniciar sesión</button>
                    </NavLink>

                    <NavLink to="/registro" className="myLink">
                        <button className="edit-button">Crear cuenta</button>
                    </NavLink>
                </div>
            </div>
        )
    }

    if (!profile || Object.keys(profile).length === 0) {
        return (
            <div className="profile-container">
                <h2>Cargando perfil...</h2>
            </div>
        )
    }


    return (
        <div className="profile-container">

            {confirmacionBorrar && (
                <div className="adm-editar-form">
                    <div className="adm-editar-container">
                        <h2>¿Seguro que querés eliminar tu cuenta?</h2>
                        <div className="adm-botones-editar">
                            <button className="adm-form-btn" onClick={eliminarCuenta}>
                                Eliminar
                            </button>
                            <button
                                className="adm-form-btn"
                                onClick={() => setConfirmacionBorrar(false)}
                            >
                                Cancelar
                            </button>
                        </div>
                    </div>
                </div>
            )}

            <div className="profile-card">
                <AvatarPerfilComponent className="profile-avatar" />
                <h2 className="profile-name">{profile.firstname} {profile.lastname}</h2>
                <p className="profile-email">{profile.email}</p>
                <div className="profile-info">
                    <h3>Información</h3>
                    <p><strong>País:</strong> Argentina</p>
                    <p><strong>Miembro desde: </strong>{profile.creationDate ? profile.creationDate.slice(0, 10) : 'admin'}</p>
                    <p><strong>Rol: </strong>{profile?.role.slice(5)}</p>
                </div>
                <NavLink to='/' className='logo myLink'>
                    <button className='edit-button' onClick={() => cerrarSesion()}>Cerrar Sesión</button>
                </NavLink> <button className='delete-button' onClick={() => setConfirmacionBorrar(true)}>Eliminar Cuenta</button>
            </div>

            <div className="profile-tabs">
                {['favoritos', 'reservas', 'reviews', 'tickets'].map(tab => (
                    <button
                        key={tab}
                        className={`tab-btn ${activeSection === tab ? 'active' : ''}`}
                        onClick={() => setActiveSection(tab)}
                    >
                        {tab.charAt(0).toUpperCase() + tab.slice(1)}
                    </button>
                ))}
            </div>

            <div className="profile-section-content">

                {activeSection === 'favoritos' && (
                    destinosFavoritos.length ? (
                        <div className="favs-container">
                            {destinosFavoritos.map(destino => (
                                <DestinoFavoritoComponent
                                    key={destino.id}
                                    destino={destino}
                                    addFavorite={addFavorite}
                                    removeFavorite={removeFavorite}
                                    token={user.token}
                                />
                            ))}
                        </div>
                    ) : <EmptyState text="No tenés destinos favoritos aún" />
                )}

                {activeSection === 'reservas' && (
                    misReservas.length ? (
                        <div className="favs-container">
                            {misReservas.map(reserva => (
                                <ReservaUserComponent
                                    key={reserva.id}
                                    reserva={reserva}
                                    actualizarReviews={actualizarReviews}
                                    cancelarReserva={cancelarReserva}
                                />
                            ))}
                        </div>
                    ) : <EmptyState text="No tenés reservas realizadas" />
                )}

                {activeSection === 'reviews' && (
                    misReviews?.length ? (
                        <div className="favs-container">
                            {misReviews.map(review => (
                                <MisReviewsComponent
                                    key={review.id}
                                    review={review}
                                    eliminarReview={borrarReview}
                                />
                            ))}
                        </div>
                    ) : <EmptyState text="Todavía no escribiste reseñas" />
                )}

                {activeSection === 'tickets' && (
                    <EmptyState text="Próximamente vas a poder gestionar tus tickets 🎫" />
                )}

            </div>
        </div>
    )
}
