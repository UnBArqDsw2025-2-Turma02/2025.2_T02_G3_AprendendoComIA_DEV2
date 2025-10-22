import { useState, useEffect } from 'react'
import { useAuth } from '../context/AuthContext'
import Navbar from '../components/Navbar'
import { Link } from 'react-router-dom'
import { MessageSquare, BookOpen, Trophy, ListChecks, Flame, Target, Clock, Star, Zap, Heart, Gem, Play, Lock, CheckCircle } from 'lucide-react'
import axios from 'axios'

export default function Dashboard() {
  const { user } = useAuth()
  const [stats, setStats] = useState(null)
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    loadDashboardStats()
  }, [])

  const loadDashboardStats = async () => {
    try {
      const response = await axios.get('/api/dashboard/stats', { withCredentials: true })
      setStats(response.data)
    } catch (error) {
      console.error('Error loading dashboard stats:', error)
    } finally {
      setLoading(false)
    }
  }

  // Mock data for demonstration (fallback)
  const dailyProgress = stats?.dailyProgress || 8
  const dailyGoal = user?.dailyGoalMinutes || 15
  const progressPercentage = (dailyProgress / dailyGoal) * 100

  const lessons = [
    { id: 1, title: "Greetings", type: "conversation", completed: true, xp: 20 },
    { id: 2, title: "Numbers", type: "vocabulary", completed: true, xp: 15 },
    { id: 3, title: "Family", type: "grammar", completed: false, xp: 25 },
    { id: 4, title: "Colors", type: "vocabulary", completed: false, xp: 20 },
    { id: 5, title: "Food", type: "conversation", completed: false, xp: 30, locked: true },
  ]

  return (
    <div className="min-h-screen bg-gradient-to-br from-slate-50 to-blue-50">
      <Navbar />
      
      <main className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        {/* Welcome Section */}
        <div className="mb-8">
          <div className="flex items-center justify-between">
            <div>
              <h1 className="text-4xl font-bold text-gray-900 mb-2">
                Olá, {user?.name}! 👋
              </h1>
              <p className="text-xl text-gray-600">
                Continue seu aprendizado de inglês hoje
              </p>
            </div>
            <div className="flex items-center gap-4">
              <div className="flex items-center gap-2 bg-white px-4 py-2 rounded-2xl shadow-lg">
                <Heart className="heart" size={20} />
                <span className="font-bold text-lg">5</span>
              </div>
              <div className="flex items-center gap-2 bg-white px-4 py-2 rounded-2xl shadow-lg">
                <Gem className="gem" size={20} />
                <span className="font-bold text-lg">1,250</span>
              </div>
            </div>
          </div>
        </div>

        {/* Stats Cards */}
        <div className="grid grid-cols-1 md:grid-cols-4 gap-6 mb-8">
          <div className="card">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-sm text-gray-600 mb-1">Streak</p>
                <p className="text-3xl font-bold text-orange-600">{user?.streakDays || 0}</p>
                <p className="text-xs text-gray-500">dias seguidos</p>
              </div>
              <div className="w-16 h-16 bg-gradient-to-r from-orange-400 to-red-500 rounded-2xl flex items-center justify-center">
                <Flame className="streak-fire" size={32} />
              </div>
            </div>
          </div>

          <div className="card">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-sm text-gray-600 mb-1">XP Hoje</p>
                <p className="text-3xl font-bold text-blue-600">45</p>
                <p className="text-xs text-gray-500">pontos</p>
              </div>
              <div className="w-16 h-16 bg-gradient-to-r from-blue-400 to-cyan-500 rounded-2xl flex items-center justify-center">
                <Zap className="text-white" size={32} />
              </div>
            </div>
          </div>

          <div className="card">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-sm text-gray-600 mb-1">Meta Diária</p>
                <p className="text-3xl font-bold text-green-600">{dailyGoal}</p>
                <p className="text-xs text-gray-500">minutos</p>
              </div>
              <div className="w-16 h-16 bg-gradient-to-r from-green-400 to-emerald-500 rounded-2xl flex items-center justify-center">
                <Target className="text-white" size={32} />
              </div>
            </div>
          </div>

          <div className="card">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-sm text-gray-600 mb-1">Nível</p>
                <p className="text-3xl font-bold text-purple-600">{user?.cefrLevel || 'A2'}</p>
                <p className="text-xs text-gray-500">atual</p>
              </div>
              <div className="w-16 h-16 bg-gradient-to-r from-purple-400 to-pink-500 rounded-2xl flex items-center justify-center">
                <Star className="text-white" size={32} />
              </div>
            </div>
          </div>
        </div>

        {/* Daily Progress */}
        <div className="card mb-8">
          <div className="flex items-center justify-between mb-6">
            <h2 className="text-2xl font-bold text-gray-900">Progresso Diário</h2>
            <div className="flex items-center gap-2">
              <Clock className="text-gray-500" size={20} />
              <span className="text-gray-600">{dailyProgress} / {dailyGoal} min</span>
            </div>
          </div>
          
          <div className="progress-bar mb-4">
            <div 
              className="progress-fill" 
              style={{ width: `${Math.min(progressPercentage, 100)}%` }}
            ></div>
          </div>
          
          <div className="flex justify-between text-sm text-gray-600">
            <span>Continue para atingir sua meta!</span>
            <span>{Math.round(progressPercentage)}% completo</span>
          </div>
        </div>

        {/* Lessons Section */}
        <div className="mb-8">
          <div className="flex items-center justify-between mb-6">
            <h2 className="text-2xl font-bold text-gray-900">Suas Lições</h2>
            <Link to="/tasks" className="text-green-600 font-semibold hover:text-green-700 transition-colors">
              Ver todas →
            </Link>
          </div>
          
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            {lessons.map((lesson, index) => (
              <div 
                key={lesson.id}
                className={`lesson-card ${lesson.completed ? 'completed' : ''} ${lesson.locked ? 'locked' : ''}`}
              >
                <div className="flex items-center justify-between mb-4">
                  <div className="flex items-center gap-3">
                    {lesson.completed ? (
                      <CheckCircle className="text-green-500" size={24} />
                    ) : lesson.locked ? (
                      <Lock className="text-gray-400" size={24} />
                    ) : (
                      <Play className="text-green-500" size={24} />
                    )}
                    <div>
                      <h3 className="font-bold text-lg">{lesson.title}</h3>
                      <p className="text-sm text-gray-600 capitalize">{lesson.type}</p>
                    </div>
                  </div>
                  <div className="xp-badge">
                    +{lesson.xp} XP
                  </div>
                </div>
                
                <div className="flex items-center justify-between">
                  <div className="flex items-center gap-2">
                    <div className={`w-3 h-3 rounded-full ${lesson.completed ? 'bg-green-500' : lesson.locked ? 'bg-gray-300' : 'bg-green-300'}`}></div>
                    <span className="text-sm text-gray-600">
                      {lesson.completed ? 'Concluída' : lesson.locked ? 'Bloqueada' : 'Disponível'}
                    </span>
                  </div>
                  {!lesson.locked && (
                    <button className="btn-primary text-sm px-4 py-2">
                      {lesson.completed ? 'Revisar' : 'Começar'}
                    </button>
                  )}
                </div>
              </div>
            ))}
          </div>
        </div>

        {/* Quick Actions */}
        <div className="mb-8">
          <h2 className="text-2xl font-bold text-gray-900 mb-6">Acesso Rápido</h2>
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
            <Link to="/chat" className="card-hover group">
              <div className="flex items-center gap-4">
                <div className="w-16 h-16 bg-gradient-to-r from-green-500 to-emerald-600 rounded-2xl flex items-center justify-center group-hover:scale-110 transition-transform duration-300">
                  <MessageSquare className="text-white" size={32} />
                </div>
                <div>
                  <h3 className="text-xl font-bold mb-1">Tutor IA</h3>
                  <p className="text-gray-600">Conversação inteligente</p>
                </div>
              </div>
            </Link>

            <Link to="/vocabulary" className="card-hover group">
              <div className="flex items-center gap-4">
                <div className="w-16 h-16 bg-gradient-to-r from-purple-500 to-pink-600 rounded-2xl flex items-center justify-center group-hover:scale-110 transition-transform duration-300">
                  <BookOpen className="text-white" size={32} />
                </div>
                <div>
                  <h3 className="text-xl font-bold mb-1">Vocabulário</h3>
                  <p className="text-gray-600">Revisar palavras</p>
                </div>
              </div>
            </Link>

            <Link to="/gamification" className="card-hover group">
              <div className="flex items-center gap-4">
                <div className="w-16 h-16 bg-gradient-to-r from-yellow-500 to-orange-600 rounded-2xl flex items-center justify-center group-hover:scale-110 transition-transform duration-300">
                  <Trophy className="text-white" size={32} />
                </div>
                <div>
                  <h3 className="text-xl font-bold mb-1">Ranking</h3>
                  <p className="text-gray-600">Ver posição</p>
                </div>
              </div>
            </Link>

            <Link to="/tasks" className="card-hover group">
              <div className="flex items-center gap-4">
                <div className="w-16 h-16 bg-gradient-to-r from-blue-500 to-cyan-600 rounded-2xl flex items-center justify-center group-hover:scale-110 transition-transform duration-300">
                  <ListChecks className="text-white" size={32} />
                </div>
                <div>
                  <h3 className="text-xl font-bold mb-1">Exercícios</h3>
                  <p className="text-gray-600">Praticar mais</p>
                </div>
              </div>
            </Link>
          </div>
        </div>

        {/* Achievement Section */}
        <div className="card">
          <h2 className="text-2xl font-bold text-gray-900 mb-6">Conquistas Recentes</h2>
          <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
            <div className="text-center">
              <div className="w-16 h-16 bg-gradient-to-r from-green-500 to-emerald-600 rounded-full flex items-center justify-center mx-auto mb-4">
                <Flame className="text-white" size={32} />
              </div>
              <h3 className="font-bold mb-2">Streak de 7 dias</h3>
              <p className="text-sm text-gray-600">Continue assim!</p>
            </div>
            
            <div className="text-center">
              <div className="w-16 h-16 bg-gradient-to-r from-blue-500 to-cyan-600 rounded-full flex items-center justify-center mx-auto mb-4">
                <Zap className="text-white" size={32} />
              </div>
              <h3 className="font-bold mb-2">100 XP em um dia</h3>
              <p className="text-sm text-gray-600">Excelente trabalho!</p>
            </div>
            
            <div className="text-center">
              <div className="w-16 h-16 bg-gradient-to-r from-purple-500 to-pink-600 rounded-full flex items-center justify-center mx-auto mb-4">
                <Trophy className="text-white" size={32} />
              </div>
              <h3 className="font-bold mb-2">Primeira lição</h3>
              <p className="text-sm text-gray-600">Bem-vindo!</p>
            </div>
          </div>
        </div>
      </main>

      {/* Floating Action Button */}
      <Link to="/chat" className="floating-action">
        <Play size={24} />
      </Link>
    </div>
  )
}

