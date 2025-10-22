import { Link, useNavigate, useLocation } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'
import { Home, MessageSquare, BookOpen, Trophy, ListChecks, LogOut, Heart, Gem, Settings, Shield, Menu, ChevronRight } from 'lucide-react'
import { useState } from 'react'
import { Brain } from 'lucide-react'
import ConversationImage from '../assets/images/conversation.svg'
import VocabularyImage from '../assets/images/vocabulary.svg'
import GamificationImage from '../assets/images/gamification.svg'
import ProgressImage from '../assets/images/progress.svg'

export default function Navbar() {
  const { user, logout } = useAuth()
  const navigate = useNavigate()
  const location = useLocation()
  const [showSidebar, setShowSidebar] = useState(false)

  const handleLogout = async () => {
    await logout()
    navigate('/')
  }

  if (!user) return null

  const services = [
    { 
      path: '/dashboard', 
      icon: Home, 
      label: 'Dashboard',
      description: 'Visão geral do progresso',
      category: 'Principal'
    },
    { 
      path: '/chat', 
      icon: ConversationImage, 
      label: 'Tutor IA',
      description: 'Conversas inteligentes com IA',
      category: 'Aprendizado'
    },
    { 
      path: '/vocabulary', 
      icon: VocabularyImage, 
      label: 'Vocabulário',
      description: 'Palavras e frases essenciais',
      category: 'Aprendizado'
    },
    { 
      path: '/tasks', 
      icon: ProgressImage, 
      label: 'Exercícios',
      description: 'Prática e lições interativas',
      category: 'Aprendizado'
    },
    { 
      path: '/gamification', 
      icon: GamificationImage, 
      label: 'Ranking',
      description: 'Competição e conquistas',
      category: 'Gamificação'
    },
  ]

  const isActive = (path) => location.pathname === path

  return (
    <>
      <nav className="navbar">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex justify-between h-16">
            {/* Left Side - Menu Button */}
            <div className="flex items-center">
              <div className="relative">
                <button 
                  className="flex items-center gap-2 px-4 py-2 rounded-xl text-gray-600 hover:bg-gray-100 hover:text-gray-900 transition-all duration-200"
                  onClick={() => setShowSidebar(!showSidebar)}
                >
                  <Menu size={20} />
                  <span className="text-sm font-medium">Menu</span>
                  <ChevronRight size={16} className={`transition-transform duration-200 ${showSidebar ? 'rotate-90' : ''}`} />
                </button>
              </div>
            </div>

            {/* Center - Logo */}
            <div className="flex items-center">
              <Link to="/dashboard" className="flex items-center gap-3">
                <div className="w-10 h-10 rounded-xl flex items-center justify-center">
                  <Brain className="w-10 h-10 text-blue-600" />
                </div>
                <span className="text-2xl font-bold text-gradient">Linguo</span>
              </Link>
            </div>

            {/* Right Side - User Info */}
            <div className="flex items-center space-x-3">
              {/* User Info */}
              <div className="flex items-center gap-3">
                {/* User Avatar */}
                <div className="w-8 h-8 bg-gradient-to-r from-blue-500 to-blue-600 rounded-full flex items-center justify-center text-white text-xs font-bold overflow-hidden">
                  {user.profilePhoto ? (
                    <img 
                      src={user.profilePhoto} 
                      alt="Foto de perfil" 
                      className="w-full h-full object-cover rounded-full"
                    />
                  ) : (
                    user.name ? 
                      user.name.split(' ').map(n => n[0]).join('').toUpperCase() : 
                      'U'
                  )}
                </div>
                
                <div className="text-right hidden sm:block">
                  <div className="text-sm font-semibold text-gray-900">{user.name}</div>
                  <div className="text-xs text-gray-500">
                    <span className="level-badge text-xs px-1.5 py-0.5">{user.cefrLevel}</span>
                  </div>
                </div>
                
                <div className="flex items-center gap-2">
                  <button
                    onClick={handleLogout}
                    className="flex items-center gap-2 px-3 py-2 text-gray-600 hover:bg-gray-100 rounded-lg transition-colors"
                    title="Sair"
                  >
                    <LogOut size={16} />
                    <span className="text-sm">Sair</span>
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </nav>

      {/* Overlay to close sidebar when clicking outside */}
      {showSidebar && (
        <div 
          className="fixed inset-0 bg-black bg-opacity-25 z-40"
          onClick={() => setShowSidebar(false)}
        />
      )}

      {/* Left Sidebar that appears on click */}
      {showSidebar && (
        <div 
          className="fixed top-16 left-0 w-80 bg-white shadow-2xl border-r border-gray-200 z-50 h-screen overflow-y-auto"
        >
          <div className="p-6">
            <h3 className="text-lg font-semibold text-gray-900 mb-4">Serviços Disponíveis</h3>
            
            {/* Group services by category */}
            {['Principal', 'Aprendizado', 'Gamificação'].map(category => (
              <div key={category} className="mb-6">
                <h4 className="text-sm font-medium text-gray-500 uppercase tracking-wide mb-3">
                  {category}
                </h4>
                <div className="space-y-2">
                  {services
                    .filter(service => service.category === category)
                    .map((service) => {
                      const Icon = service.icon
                      return (
                        <Link
                          key={service.path}
                          to={service.path}
                          className={`flex items-center gap-3 p-3 rounded-lg transition-all duration-200 ${
                            isActive(service.path)
                              ? 'bg-blue-50 text-blue-700 border-l-4 border-blue-500'
                              : 'text-gray-700 hover:bg-gray-50 hover:text-gray-900'
                          }`}
                        >
                          {typeof service.icon === 'string' ? (
                            <img src={service.icon} alt={service.label} className="w-5 h-5 flex-shrink-0" />
                          ) : (
                            <Icon size={20} className="flex-shrink-0" />
                          )}
                          <div className="flex-1 min-w-0">
                            <div className="font-medium text-sm">{service.label}</div>
                            <div className="text-xs text-gray-500 truncate">{service.description}</div>
                          </div>
                        </Link>
                      )
                    })}
                </div>
              </div>
            ))}

            {/* Quick Actions */}
            <div className="mt-6 pt-6 border-t border-gray-200">
              <h4 className="text-sm font-medium text-gray-500 uppercase tracking-wide mb-3">
                Ações Rápidas
              </h4>
              <div className="space-y-2">
                <Link
                  to="/settings"
                  className="flex items-center gap-3 p-3 rounded-lg text-gray-700 hover:bg-gray-50 hover:text-gray-900 transition-all duration-200"
                >
                  <Settings size={20} />
                  <span className="text-sm">Configurações</span>
                </Link>
                {user.email === 'admin@lingu.com' && (
                  <Link
                    to="/admin"
                    className="flex items-center gap-3 p-3 rounded-lg text-gray-700 hover:bg-gray-50 hover:text-gray-900 transition-all duration-200"
                  >
                    <Shield size={20} />
                    <span className="text-sm">Painel Admin</span>
                  </Link>
                )}
              </div>
            </div>
          </div>
        </div>
      )}
    </>
  )
}