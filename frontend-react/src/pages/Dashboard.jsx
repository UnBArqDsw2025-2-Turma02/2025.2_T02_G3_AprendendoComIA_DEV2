import { useAuth } from '../context/AuthContext'
import Navbar from '../components/Navbar'
import { Link } from 'react-router-dom'
import { MessageSquare, BookOpen, Trophy, ListChecks, Flame, Target, Clock } from 'lucide-react'

export default function Dashboard() {
  const { user } = useAuth()

  return (
    <div className="min-h-screen bg-gray-50">
      <Navbar />
      
      <main className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        <div className="mb-8">
          <h1 className="text-3xl font-bold text-gray-900">
            Olá, {user?.name}! 👋
          </h1>
          <p className="text-gray-600 mt-1">
            Continue seu aprendizado de inglês hoje
          </p>
        </div>

        {/* Stats */}
        <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mb-8">
          <div className="card">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-sm text-gray-600">Streak</p>
                <p className="text-3xl font-bold text-primary-600">{user?.streakDays || 0}</p>
                <p className="text-xs text-gray-500">dias seguidos</p>
              </div>
              <div className="w-12 h-12 bg-orange-100 rounded-lg flex items-center justify-center">
                <Flame className="text-orange-600" size={24} />
              </div>
            </div>
          </div>

          <div className="card">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-sm text-gray-600">Meta Diária</p>
                <p className="text-3xl font-bold text-primary-600">{user?.dailyGoalMinutes || 15}</p>
                <p className="text-xs text-gray-500">minutos</p>
              </div>
              <div className="w-12 h-12 bg-green-100 rounded-lg flex items-center justify-center">
                <Target className="text-green-600" size={24} />
              </div>
            </div>
          </div>

          <div className="card">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-sm text-gray-600">Tempo Total</p>
                <p className="text-3xl font-bold text-primary-600">{user?.totalMinutes || 0}</p>
                <p className="text-xs text-gray-500">minutos</p>
              </div>
              <div className="w-12 h-12 bg-blue-100 rounded-lg flex items-center justify-center">
                <Clock className="text-blue-600" size={24} />
              </div>
            </div>
          </div>
        </div>

        {/* Quick Actions */}
        <div className="mb-8">
          <h2 className="text-xl font-semibold text-gray-900 mb-4">Acesso Rápido</h2>
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4">
            <Link to="/chat" className="card hover:shadow-md transition-shadow cursor-pointer group">
              <div className="flex items-center gap-4">
                <div className="w-12 h-12 bg-primary-100 rounded-lg flex items-center justify-center group-hover:bg-primary-200 transition-colors">
                  <MessageSquare className="text-primary-600" size={24} />
                </div>
                <div>
                  <h3 className="font-semibold">Tutor IA</h3>
                  <p className="text-sm text-gray-600">Conversação</p>
                </div>
              </div>
            </Link>

            <Link to="/vocabulary" className="card hover:shadow-md transition-shadow cursor-pointer group">
              <div className="flex items-center gap-4">
                <div className="w-12 h-12 bg-purple-100 rounded-lg flex items-center justify-center group-hover:bg-purple-200 transition-colors">
                  <BookOpen className="text-purple-600" size={24} />
                </div>
                <div>
                  <h3 className="font-semibold">Vocabulário</h3>
                  <p className="text-sm text-gray-600">Revisar palavras</p>
                </div>
              </div>
            </Link>

            <Link to="/gamification" className="card hover:shadow-md transition-shadow cursor-pointer group">
              <div className="flex items-center gap-4">
                <div className="w-12 h-12 bg-yellow-100 rounded-lg flex items-center justify-center group-hover:bg-yellow-200 transition-colors">
                  <Trophy className="text-yellow-600" size={24} />
                </div>
                <div>
                  <h3 className="font-semibold">Ranking</h3>
                  <p className="text-sm text-gray-600">Ver posição</p>
                </div>
              </div>
            </Link>

            <Link to="/tasks" className="card hover:shadow-md transition-shadow cursor-pointer group">
              <div className="flex items-center gap-4">
                <div className="w-12 h-12 bg-green-100 rounded-lg flex items-center justify-center group-hover:bg-green-200 transition-colors">
                  <ListChecks className="text-green-600" size={24} />
                </div>
                <div>
                  <h3 className="font-semibold">Exercícios</h3>
                  <p className="text-sm text-gray-600">Praticar</p>
                </div>
              </div>
            </Link>
          </div>
        </div>

        {/* Progress Section */}
        <div className="card">
          <h2 className="text-xl font-semibold text-gray-900 mb-4">Seu Progresso</h2>
          <div className="space-y-4">
            <div>
              <div className="flex justify-between text-sm mb-1">
                <span className="text-gray-600">Progresso do Dia</span>
                <span className="font-medium">0 / {user?.dailyGoalMinutes || 15} min</span>
              </div>
              <div className="w-full bg-gray-200 rounded-full h-2">
                <div className="bg-primary-600 h-2 rounded-full" style={{ width: '0%' }}></div>
              </div>
            </div>

            <div className="pt-4 border-t border-gray-200">
              <p className="text-sm text-gray-600">
                <strong>Nível Atual:</strong> {user?.cefrLevel}
              </p>
              <p className="text-sm text-gray-600 mt-1">
                Continue praticando para melhorar seu inglês! 🚀
              </p>
            </div>
          </div>
        </div>
      </main>
    </div>
  )
}

