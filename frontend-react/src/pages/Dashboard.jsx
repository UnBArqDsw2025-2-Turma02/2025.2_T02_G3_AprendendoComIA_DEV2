import { useState, useEffect } from 'react'
import { useAuth } from '../context/AuthContext'
import Navbar from '../components/Navbar'
import { Link } from 'react-router-dom'
import { Clock, Play, Lock, CheckCircle, Heart, Gem, TrendingUp, ArrowRight } from 'lucide-react'
import axios from 'axios'
import StreakImage from '../assets/images/streak.svg'
import XpImage from '../assets/images/xp.svg'
import TargetImage from '../assets/images/target.svg'
import LevelImage from '../assets/images/level.svg'
import ConversationImage from '../assets/images/conversation.svg'
import VocabularyImage from '../assets/images/vocabulary.svg'
import GamificationImage from '../assets/images/gamification.svg'
import ProgressImage from '../assets/images/progress.svg'

export default function Dashboard() {
  const { user } = useAuth()
  const [stats, setStats] = useState(null)
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    loadDashboardStats()
  }, [])

  const loadDashboardStats = async () => {
    try {
      const response = await axios.get(`/api/dashboard/stats/${user.id}`, { withCredentials: true })
      setStats(response.data)
    } catch (error) {
      console.error('Error loading dashboard stats:', error)
      // Fallback to mock data
      setStats({
        streakDays: 5,
        totalXp: 250,
        level: 3,
        dailyGoalMinutes: 15,
        todayMinutes: 8,
        cefrLevel: 'A2',
        totalReviews: 45,
        totalChatSessions: 12,
        totalVocabularyCards: 200,
        progressPercentage: 53
      })
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
      
      <main className="relative z-10 max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
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
              <div className="w-16 h-16 rounded-2xl flex items-center justify-center">
                <img src={StreakImage} alt="Streak" className="w-16 h-16" />
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
              <div className="w-16 h-16 rounded-2xl flex items-center justify-center">
                <img src={XpImage} alt="XP" className="w-16 h-16" />
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
              <div className="w-16 h-16 rounded-2xl flex items-center justify-center">
                <img src={TargetImage} alt="Meta Diária" className="w-16 h-16" />
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
              <div className="w-16 h-16 rounded-2xl flex items-center justify-center">
                <img src={LevelImage} alt="Nível" className="w-16 h-16" />
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
          
          {/* Progress Overview */}
          <div className="bg-gradient-to-r from-blue-50 to-cyan-50 rounded-2xl p-6 mb-6 border border-blue-200">
            <div className="flex items-center justify-between mb-4">
              <h3 className="text-lg font-bold text-blue-900">Progresso das Lições</h3>
              <div className="flex items-center gap-2 text-blue-700">
                <TrendingUp size={20} />
                <span className="font-semibold">
                  {lessons.filter(l => l.completed).length} / {lessons.length} concluídas
                </span>
              </div>
            </div>
            <div className="w-full bg-blue-200 rounded-full h-3 mb-4">
              <div 
                className="bg-gradient-to-r from-blue-500 to-cyan-500 h-3 rounded-full transition-all duration-300"
                style={{ width: `${(lessons.filter(l => l.completed).length / lessons.length) * 100}%` }}
              ></div>
            </div>
            <div className="flex justify-between text-sm text-blue-700">
              <span>Continue estudando para desbloquear novas lições!</span>
              <span>{Math.round((lessons.filter(l => l.completed).length / lessons.length) * 100)}% completo</span>
            </div>
          </div>
          
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            {lessons.map((lesson, index) => (
              <div 
                key={lesson.id}
                className={`bg-white rounded-2xl p-6 shadow-lg hover:shadow-xl transition-all duration-300 hover:scale-105 ${
                  lesson.completed ? 'border-2 border-green-200 bg-green-50' : 
                  lesson.locked ? 'opacity-60' : 'border-2 border-blue-200 bg-blue-50'
                }`}
              >
                <div className="flex items-center justify-between mb-4">
                  <div className="flex items-center gap-3">
                    {lesson.completed ? (
                      <div className="w-10 h-10 bg-gradient-to-r from-green-500 to-emerald-500 rounded-xl flex items-center justify-center">
                        <CheckCircle className="text-white" size={20} />
                      </div>
                    ) : lesson.locked ? (
                      <div className="w-10 h-10 bg-gray-300 rounded-xl flex items-center justify-center">
                        <Lock className="text-gray-500" size={20} />
                      </div>
                    ) : (
                      <div className="w-10 h-10 bg-gradient-to-r from-blue-500 to-cyan-500 rounded-xl flex items-center justify-center">
                        <Play className="text-white" size={20} />
                      </div>
                    )}
                    <div>
                      <h3 className="font-bold text-lg text-gray-900">{lesson.title}</h3>
                      <p className="text-sm text-gray-600 capitalize">{lesson.type}</p>
                    </div>
                  </div>
                  <div className="bg-gradient-to-r from-yellow-400 to-orange-400 text-white text-xs font-bold px-3 py-1 rounded-full">
                    +{lesson.xp} XP
                  </div>
                </div>
                
                <div className="flex items-center justify-between">
                  <div className="flex items-center gap-2">
                    <div className={`w-3 h-3 rounded-full ${
                      lesson.completed ? 'bg-green-500' : 
                      lesson.locked ? 'bg-gray-400' : 'bg-blue-500'
                    }`}></div>
                    <span className="text-sm font-medium text-gray-700">
                      {lesson.completed ? 'Concluída' : lesson.locked ? 'Bloqueada' : 'Disponível'}
                    </span>
                  </div>
                  
                  {!lesson.locked && (
                    <Link 
                      to="/tasks" 
                      className={`px-4 py-2 rounded-xl font-semibold text-sm transition-all duration-300 hover:scale-105 ${
                        lesson.completed 
                          ? 'bg-gradient-to-r from-green-500 to-emerald-500 text-white shadow-lg hover:shadow-xl' 
                          : 'bg-gradient-to-r from-blue-500 to-cyan-500 text-white shadow-lg hover:shadow-xl'
                      }`}
                    >
                      {lesson.completed ? 'Revisar' : 'Começar'}
                    </Link>
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
                <div className="w-16 h-16 rounded-2xl flex items-center justify-center group-hover:scale-110 transition-transform duration-300">
                  <img src={ConversationImage} alt="Tutor IA" className="w-16 h-16" />
                </div>
                <div>
                  <h3 className="text-xl font-bold mb-1">Tutor IA</h3>
                  <p className="text-gray-600">Conversação inteligente</p>
                </div>
              </div>
            </Link>

            <Link to="/vocabulary" className="card-hover group">
              <div className="flex items-center gap-4">
                <div className="w-16 h-16 rounded-2xl flex items-center justify-center group-hover:scale-110 transition-transform duration-300">
                  <img src={VocabularyImage} alt="Vocabulário" className="w-16 h-16" />
                </div>
                <div>
                  <h3 className="text-xl font-bold mb-1">Vocabulário</h3>
                  <p className="text-gray-600">Revisar palavras</p>
                </div>
              </div>
            </Link>

            <Link to="/gamification" className="card-hover group">
              <div className="flex items-center gap-4">
                <div className="w-16 h-16 rounded-2xl flex items-center justify-center group-hover:scale-110 transition-transform duration-300">
                  <img src={GamificationImage} alt="Ranking" className="w-16 h-16" />
                </div>
                <div>
                  <h3 className="text-xl font-bold mb-1">Ranking</h3>
                  <p className="text-gray-600">Ver posição</p>
                </div>
              </div>
            </Link>

            <Link to="/tasks" className="card-hover group">
              <div className="flex items-center gap-4">
                <div className="w-16 h-16 rounded-2xl flex items-center justify-center group-hover:scale-110 transition-transform duration-300">
                  <img src={ProgressImage} alt="Exercícios" className="w-16 h-16" />
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
              <div className="w-16 h-16 rounded-full flex items-center justify-center mx-auto mb-4">
                <img src={StreakImage} alt="Streak de 7 dias" className="w-16 h-16" />
              </div>
              <h3 className="font-bold mb-2">Streak de 7 dias</h3>
              <p className="text-sm text-gray-600">Continue assim!</p>
            </div>
            
            <div className="text-center">
              <div className="w-16 h-16 rounded-full flex items-center justify-center mx-auto mb-4">
                <img src={XpImage} alt="100 XP em um dia" className="w-16 h-16" />
              </div>
              <h3 className="font-bold mb-2">100 XP em um dia</h3>
              <p className="text-sm text-gray-600">Excelente trabalho!</p>
            </div>
            
            <div className="text-center">
              <div className="w-16 h-16 rounded-full flex items-center justify-center mx-auto mb-4">
                <img src={GamificationImage} alt="Primeira lição" className="w-16 h-16" />
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

