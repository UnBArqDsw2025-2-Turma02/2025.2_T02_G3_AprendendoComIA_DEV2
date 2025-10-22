import { useState, useEffect } from 'react'
import { useAuth } from '../context/AuthContext'
import Navbar from '../components/Navbar'
import axios from 'axios'
import { Trophy, Medal, Flame, Target, Users, Zap, Star, Crown, Award, TrendingUp, Calendar, Clock, BookOpen, MessageSquare } from 'lucide-react'

export default function Gamification() {
  const { user } = useAuth()
  const [leaders, setLeaders] = useState([])
  const [groups, setGroups] = useState([])
  const [goals, setGoals] = useState([])
  const [activeTab, setActiveTab] = useState('leaderboard')
  const [loading, setLoading] = useState(true)

  // Mock data for demonstration
  const mockLeaders = [
    { id: 1, name: "Ana Silva", team: "Brasil", xp: 2450, streak: 15, reactions: { clap: 12, fire: 8, flex: 5 } },
    { id: 2, name: "João Santos", team: "Brasil", xp: 2380, streak: 12, reactions: { clap: 10, fire: 6, flex: 3 } },
    { id: 3, name: "Maria Costa", team: "Brasil", xp: 2200, streak: 18, reactions: { clap: 15, fire: 10, flex: 7 } },
    { id: 4, name: "Pedro Lima", team: "Brasil", xp: 1950, streak: 8, reactions: { clap: 5, fire: 3, flex: 2 } },
    { id: 5, name: "Carla Oliveira", team: "Brasil", xp: 1800, streak: 6, reactions: { clap: 8, fire: 4, flex: 1 } },
  ]

  const mockGoals = [
    { id: 1, title: "Streak de 7 dias", desc: "Estude inglês por 7 dias consecutivos", progress: 5, target: 7, unit: "dias", icon: Flame, color: "orange" },
    { id: 2, title: "1000 XP", desc: "Ganhe 1000 pontos de experiência", progress: 750, target: 1000, unit: "XP", icon: Zap, color: "blue" },
    { id: 3, title: "50 Lições", desc: "Complete 50 lições de vocabulário", progress: 32, target: 50, unit: "lições", icon: BookOpen, color: "green" },
    { id: 4, title: "Conversação", desc: "Pratique conversação por 10 horas", progress: 6, target: 10, unit: "horas", icon: MessageSquare, color: "purple" },
  ]

  const mockGroups = [
    { id: 1, name: "Iniciantes A1", members: 45, open: true, description: "Para quem está começando" },
    { id: 2, name: "Intermediários B1", members: 32, open: true, description: "Nível intermediário" },
    { id: 3, name: "Avançados C1", members: 18, open: false, description: "Nível avançado" },
  ]

  useEffect(() => {
    loadData()
  }, [])

  const loadData = async () => {
    try {
      const [leadersResponse, groupsResponse] = await Promise.all([
        axios.get('/api/gamification/leaderboard', { withCredentials: true }),
        axios.get('/api/gamification/groups', { withCredentials: true })
      ])
      
      setLeaders(leadersResponse.data.leaders || leadersResponse.data)
      setGroups(groupsResponse.data.groups || groupsResponse.data)
      setGoals(mockGoals) // Keep mock goals for now
      setLoading(false)
    } catch (error) {
      console.error('Error loading gamification data:', error)
      // Fallback to mock data
      setLeaders(mockLeaders)
      setGroups(mockGroups)
      setGoals(mockGoals)
      setLoading(false)
    }
  }

  if (loading) {
    return (
      <div className="min-h-screen bg-gradient-to-br from-slate-50 to-blue-50">
        <Navbar />
        <div className="flex items-center justify-center h-96">
          <div className="animate-spin rounded-full h-16 w-16 border-4 border-green-500 border-t-transparent"></div>
        </div>
      </div>
    )
  }

  return (
    <div className="min-h-screen bg-gradient-to-br from-slate-50 to-blue-50">
      <Navbar />
      
      <main className="max-w-7xl mx-auto px-4 py-8">
        {/* Header */}
        <div className="mb-8">
          <div className="flex items-center justify-between mb-6">
            <div>
              <h1 className="text-4xl font-bold text-gray-900 mb-2">Gamificação</h1>
              <p className="text-xl text-gray-600">Acompanhe seu progresso e compete com outros alunos</p>
            </div>
            
            {/* User Stats */}
            <div className="flex items-center gap-4">
              <div className="flex items-center gap-2 bg-white px-4 py-3 rounded-2xl shadow-lg">
                <Zap className="text-blue-500" size={20} />
                <span className="font-bold text-lg">1,250 XP</span>
              </div>
              <div className="flex items-center gap-2 bg-white px-4 py-3 rounded-2xl shadow-lg">
                <Flame className="streak-fire" size={20} />
                <span className="font-bold text-lg">7</span>
              </div>
              <div className="flex items-center gap-2 bg-white px-4 py-3 rounded-2xl shadow-lg">
                <Trophy className="text-yellow-500" size={20} />
                <span className="font-bold text-lg">#15</span>
              </div>
            </div>
          </div>

          {/* Quick Stats */}
          <div className="grid grid-cols-1 md:grid-cols-4 gap-6 mb-8">
            <div className="card text-center">
              <div className="w-16 h-16 bg-gradient-to-r from-green-500 to-emerald-600 rounded-2xl flex items-center justify-center mx-auto mb-4">
                <Trophy className="text-white" size={32} />
              </div>
              <h3 className="font-bold text-lg mb-2">Posição</h3>
              <p className="text-3xl font-bold text-green-600">#15</p>
              <p className="text-sm text-gray-600">de 1,250 alunos</p>
            </div>

            <div className="card text-center">
              <div className="w-16 h-16 bg-gradient-to-r from-blue-500 to-cyan-600 rounded-2xl flex items-center justify-center mx-auto mb-4">
                <Zap className="text-white" size={32} />
              </div>
              <h3 className="font-bold text-lg mb-2">XP Total</h3>
              <p className="text-3xl font-bold text-blue-600">1,250</p>
              <p className="text-sm text-gray-600">pontos</p>
            </div>

            <div className="card text-center">
              <div className="w-16 h-16 bg-gradient-to-r from-orange-500 to-red-600 rounded-2xl flex items-center justify-center mx-auto mb-4">
                <Flame className="text-white" size={32} />
              </div>
              <h3 className="font-bold text-lg mb-2">Streak</h3>
              <p className="text-3xl font-bold text-orange-600">7</p>
              <p className="text-sm text-gray-600">dias seguidos</p>
            </div>

            <div className="card text-center">
              <div className="w-16 h-16 bg-gradient-to-r from-purple-500 to-pink-600 rounded-2xl flex items-center justify-center mx-auto mb-4">
                <Star className="text-white" size={32} />
              </div>
              <h3 className="font-bold text-lg mb-2">Nível</h3>
              <p className="text-3xl font-bold text-purple-600">A2</p>
              <p className="text-sm text-gray-600">atual</p>
            </div>
          </div>
        </div>

        {/* Tabs */}
        <div className="mb-8">
          <div className="flex space-x-2 bg-white p-2 rounded-2xl shadow-lg">
            <button
              onClick={() => setActiveTab('leaderboard')}
              className={`flex-1 flex items-center justify-center gap-2 py-3 px-6 rounded-xl font-semibold transition-all duration-200 ${
                activeTab === 'leaderboard'
                  ? 'bg-green-500 text-white shadow-lg'
                  : 'text-gray-600 hover:bg-gray-100'
              }`}
            >
              <Trophy size={20} />
              Ranking
            </button>
            <button
              onClick={() => setActiveTab('goals')}
              className={`flex-1 flex items-center justify-center gap-2 py-3 px-6 rounded-xl font-semibold transition-all duration-200 ${
                activeTab === 'goals'
                  ? 'bg-green-500 text-white shadow-lg'
                  : 'text-gray-600 hover:bg-gray-100'
              }`}
            >
              <Target size={20} />
              Metas
            </button>
            <button
              onClick={() => setActiveTab('groups')}
              className={`flex-1 flex items-center justify-center gap-2 py-3 px-6 rounded-xl font-semibold transition-all duration-200 ${
                activeTab === 'groups'
                  ? 'bg-green-500 text-white shadow-lg'
                  : 'text-gray-600 hover:bg-gray-100'
              }`}
            >
              <Users size={20} />
              Grupos
            </button>
          </div>
        </div>

        {/* Content */}
        {activeTab === 'leaderboard' && (
          <div className="card">
            <div className="flex items-center justify-between mb-6">
              <h2 className="text-2xl font-bold text-gray-900">🏆 Top Alunos</h2>
              <div className="flex items-center gap-2 text-gray-600">
                <Calendar size={20} />
                <span>Atualizado hoje</span>
              </div>
            </div>
            
            <div className="space-y-4">
              {leaders.map((leader, idx) => (
                <div key={leader.id} className={`flex items-center gap-4 p-6 rounded-2xl transition-all duration-200 hover:shadow-lg ${
                  idx < 3 ? 'bg-gradient-to-r from-yellow-50 to-orange-50 border-2 border-yellow-200' : 'bg-gray-50 hover:bg-gray-100'
                }`}>
                  <div className={`w-12 h-12 rounded-2xl flex items-center justify-center font-bold text-lg ${
                    idx === 0 ? 'bg-gradient-to-r from-yellow-400 to-yellow-600 text-white' :
                    idx === 1 ? 'bg-gradient-to-r from-gray-300 to-gray-400 text-white' :
                    idx === 2 ? 'bg-gradient-to-r from-orange-400 to-orange-600 text-white' :
                    'bg-gradient-to-r from-green-400 to-green-600 text-white'
                  }`}>
                    {idx === 0 ? <Crown size={24} /> : idx + 1}
                  </div>
                  
                  <div className="flex-1">
                    <div className="flex items-center gap-3 mb-1">
                      <h3 className="font-bold text-lg">{leader.name}</h3>
                      {idx < 3 && (
                        <div className="flex items-center gap-1">
                          {idx === 0 && <span className="text-yellow-500">🥇</span>}
                          {idx === 1 && <span className="text-gray-500">🥈</span>}
                          {idx === 2 && <span className="text-orange-500">🥉</span>}
                        </div>
                      )}
                    </div>
                    <div className="flex items-center gap-4 text-sm text-gray-600">
                      <span className="flex items-center gap-1">
                        <Users size={16} />
                        {leader.team}
                      </span>
                      <span className="flex items-center gap-1">
                        <Flame className="streak-fire" size={16} />
                        {leader.streak} dias
                      </span>
                    </div>
                  </div>

                  <div className="text-right">
                    <div className="text-2xl font-bold text-green-600">{leader.xp.toLocaleString()} XP</div>
                    <div className="flex items-center gap-2 text-sm text-gray-600">
                      <TrendingUp size={16} />
                      <span>+{Math.floor(Math.random() * 100)} esta semana</span>
                    </div>
                  </div>

                  <div className="flex gap-2">
                    {Object.entries(leader.reactions).map(([emoji, count]) => (
                      count > 0 && (
                        <button key={emoji} className="flex items-center gap-1 px-3 py-1 bg-white rounded-xl text-sm hover:bg-gray-50 transition-colors">
                          <span>{emoji === 'clap' ? '👏' : emoji === 'fire' ? '🔥' : emoji === 'flex' ? '💪' : '😂'}</span>
                          <span className="font-semibold">{count}</span>
                        </button>
                      )
                    ))}
                  </div>
                </div>
              ))}
            </div>
          </div>
        )}

        {activeTab === 'goals' && (
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            {goals.map((goal) => {
              const progress = (goal.progress / goal.target) * 100
              const Icon = goal.icon
              return (
                <div key={goal.id} className="card-hover">
                  <div className="flex items-start justify-between mb-4">
                    <div className="flex items-center gap-3">
                      <div className={`w-12 h-12 bg-gradient-to-r from-${goal.color}-500 to-${goal.color}-600 rounded-2xl flex items-center justify-center`}>
                        <Icon className="text-white" size={24} />
                      </div>
                      <div>
                        <h3 className="font-bold text-lg">{goal.title}</h3>
                        <p className="text-sm text-gray-600">{goal.desc}</p>
                      </div>
                    </div>
                    <div className="text-right">
                      <div className="text-2xl font-bold text-green-600">{goal.progress}</div>
                      <div className="text-sm text-gray-500">/ {goal.target}</div>
                    </div>
                  </div>
                  
                  <div className="mb-4">
                    <div className="flex justify-between text-sm mb-2">
                      <span className="text-gray-600">Progresso</span>
                      <span className="font-semibold">{Math.round(progress)}%</span>
                    </div>
                    <div className="progress-bar">
                      <div 
                        className="progress-fill" 
                        style={{ width: `${Math.min(progress, 100)}%` }}
                      ></div>
                    </div>
                  </div>
                  
                  <div className="flex items-center justify-between">
                    <span className="text-sm text-gray-500">
                      {goal.target - goal.progress} {goal.unit} restantes
                    </span>
                    {progress >= 100 && (
                      <div className="flex items-center gap-1 text-green-600">
                        <Award size={16} />
                        <span className="font-semibold">Concluída!</span>
                      </div>
                    )}
                  </div>
                </div>
              )
            })}
          </div>
        )}

        {activeTab === 'groups' && (
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            {groups.map((group) => (
              <div key={group.id} className="card-hover">
                <div className="flex items-start justify-between mb-4">
                  <div>
                    <h3 className="font-bold text-xl mb-2">{group.name}</h3>
                    <p className="text-gray-600 mb-3">{group.description}</p>
                  </div>
                  <span className={`px-3 py-1 rounded-full text-sm font-semibold ${
                    group.open 
                      ? 'bg-green-100 text-green-700 border-2 border-green-200' 
                      : 'bg-gray-100 text-gray-700 border-2 border-gray-200'
                  }`}>
                    {group.open ? 'Aberto' : 'Fechado'}
                  </span>
                </div>
                
                <div className="flex items-center gap-4 mb-6">
                  <div className="flex items-center gap-2 text-gray-600">
                    <Users size={20} />
                    <span className="font-semibold">{group.members} membros</span>
                  </div>
                  <div className="flex items-center gap-2 text-gray-600">
                    <Clock size={20} />
                    <span className="text-sm">Ativo agora</span>
                  </div>
                </div>

                {group.open ? (
                  <button className="w-full btn-primary">
                    Participar do Grupo
                  </button>
                ) : (
                  <button className="w-full btn-secondary" disabled>
                    Grupo Fechado
                  </button>
                )}
              </div>
            ))}
          </div>
        )}
      </main>
    </div>
  )
}

