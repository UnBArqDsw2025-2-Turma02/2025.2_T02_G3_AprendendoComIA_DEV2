import { Link, useNavigate, useLocation } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'
import { Home, MessageSquare, BookOpen, Trophy, ListChecks, LogOut, BookOpen as BookIcon, Heart, Gem, Settings, Shield } from 'lucide-react'

export default function Navbar() {
  const { user, logout } = useAuth()
  const navigate = useNavigate()
  const location = useLocation()

  const handleLogout = async () => {
    await logout()
    navigate('/')
  }

  if (!user) return null

  const navItems = [
    { path: '/dashboard', icon: Home, label: 'Dashboard' },
    { path: '/chat', icon: MessageSquare, label: 'Tutor IA' },
    { path: '/vocabulary', icon: BookOpen, label: 'Vocabulário' },
    { path: '/gamification', icon: Trophy, label: 'Ranking' },
    { path: '/tasks', icon: ListChecks, label: 'Exercícios' },
  ]

  const isActive = (path) => location.pathname === path

  return (
    <nav className="navbar">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex justify-between h-16">
          <div className="flex items-center space-x-8">
            <Link to="/dashboard" className="flex items-center gap-3">
              <div className="w-10 h-10 bg-gradient-to-r from-blue-500 to-blue-600 rounded-2xl flex items-center justify-center">
                <BookIcon className="text-white" size={24} />
              </div>
              <span className="text-2xl font-bold text-gradient">Linguo</span>
            </Link>
            
            <div className="hidden md:flex space-x-2">
              {navItems.map((item) => {
                const Icon = item.icon
                return (
                  <Link
                    key={item.path}
                    to={item.path}
                    className={`flex items-center gap-2 px-4 py-2 rounded-2xl transition-all duration-200 ${
                      isActive(item.path)
                        ? 'bg-blue-100 text-blue-700 font-semibold'
                        : 'text-gray-600 hover:bg-gray-100 hover:text-gray-900'
                    }`}
                  >
                    <Icon size={20} />
                    <span>{item.label}</span>
                  </Link>
                )
              })}
            </div>
          </div>

          <div className="flex items-center space-x-4">
            {/* Hearts and Gems */}
            <div className="hidden lg:flex items-center gap-3">
              <div className="flex items-center gap-2 bg-white px-3 py-2 rounded-2xl shadow-lg">
                <Heart className="heart" size={18} />
                <span className="font-bold text-lg">5</span>
              </div>
              <div className="flex items-center gap-2 bg-white px-3 py-2 rounded-2xl shadow-lg">
                <Gem className="gem" size={18} />
                <span className="font-bold text-lg">1,250</span>
              </div>
            </div>

            {/* User Info */}
            <div className="flex items-center gap-4">
              <div className="text-right hidden sm:block">
                <div className="text-sm font-semibold text-gray-900">{user.name}</div>
                <div className="text-xs text-gray-500 flex items-center gap-1">
                  <span className="level-badge text-xs px-2 py-1">{user.cefrLevel}</span>
                </div>
              </div>
              
              <div className="flex items-center gap-2">
                <Link 
                  to="/settings"
                  className="p-2 text-gray-600 hover:bg-gray-100 rounded-2xl transition-colors"
                >
                  <Settings size={20} />
                </Link>
                
                {user.email === 'admin@lingu.com' && (
                  <Link 
                    to="/admin"
                    className="p-2 text-gray-600 hover:bg-gray-100 rounded-2xl transition-colors"
                  >
                    <Shield size={20} />
                  </Link>
                )}
                
                <button
                  onClick={handleLogout}
                  className="flex items-center gap-2 px-4 py-2 text-red-600 hover:bg-red-50 rounded-2xl transition-colors font-medium"
                >
                  <LogOut size={20} />
                  <span className="hidden md:inline">Sair</span>
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </nav>
  )
}

