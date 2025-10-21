import { useState, useEffect } from 'react'
import Navbar from '../components/Navbar'
import axios from 'axios'
import { Trophy, Medal, Flame, Target, Users } from 'lucide-react'

export default function Gamification() {
  const [leaders, setLeaders] = useState([])
  const [groups, setGroups] = useState([])
  const [goals, setGoals] = useState([])
  const [activeTab, setActiveTab] = useState('leaderboard')
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    loadData()
  }, [])

  const loadData = async () => {
    try {
      const [leaderboardRes, groupsRes, goalsRes] = await Promise.all([
        axios.get('/api/gamification/leaderboard', { withCredentials: true }),
        axios.get('/api/gamification/groups', { withCredentials: true }),
        axios.get('/api/gamification/goals', { withCredentials: true })
      ])
      
      setLeaders(leaderboardRes.data.leaders)
      setGroups(groupsRes.data.groups)
      setGoals(goalsRes.data.goals)
    } catch (error) {
      console.error('Error loading gamification data:', error)
    } finally {
      setLoading(false)
    }
  }

  if (loading) {
    return (
      <div className="min-h-screen bg-gray-50">
        <Navbar />
        <div className="flex items-center justify-center h-96">
          <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-primary-600"></div>
        </div>
      </div>
    )
  }

  return (
    <div className="min-h-screen bg-gray-50">
      <Navbar />
      
      <main className="max-w-7xl mx-auto px-4 py-8">
        <div className="mb-6">
          <h1 className="text-3xl font-bold text-gray-900">Gamificação</h1>
          <p className="text-gray-600 mt-1">Acompanhe seu progresso e compete com outros alunos</p>
        </div>

        {/* Tabs */}
        <div className="mb-6 border-b border-gray-200">
          <div className="flex space-x-8">
            <button
              onClick={() => setActiveTab('leaderboard')}
              className={`pb-4 px-1 border-b-2 font-medium text-sm transition-colors ${
                activeTab === 'leaderboard'
                  ? 'border-primary-600 text-primary-600'
                  : 'border-transparent text-gray-500 hover:text-gray-700'
              }`}
            >
              <Trophy size={20} className="inline mr-2" />
              Ranking
            </button>
            <button
              onClick={() => setActiveTab('goals')}
              className={`pb-4 px-1 border-b-2 font-medium text-sm transition-colors ${
                activeTab === 'goals'
                  ? 'border-primary-600 text-primary-600'
                  : 'border-transparent text-gray-500 hover:text-gray-700'
              }`}
            >
              <Target size={20} className="inline mr-2" />
              Metas
            </button>
            <button
              onClick={() => setActiveTab('groups')}
              className={`pb-4 px-1 border-b-2 font-medium text-sm transition-colors ${
                activeTab === 'groups'
                  ? 'border-primary-600 text-primary-600'
                  : 'border-transparent text-gray-500 hover:text-gray-700'
              }`}
            >
              <Users size={20} className="inline mr-2" />
              Grupos
            </button>
          </div>
        </div>

        {/* Content */}
        {activeTab === 'leaderboard' && (
          <div className="card">
            <h2 className="text-xl font-semibold mb-4">Top Alunos</h2>
            <div className="space-y-3">
              {leaders.map((leader, idx) => (
                <div key={leader.id} className="flex items-center gap-4 p-4 bg-gray-50 rounded-lg hover:bg-gray-100 transition-colors">
                  <div className={`w-10 h-10 rounded-full flex items-center justify-center font-bold ${
                    idx === 0 ? 'bg-yellow-400 text-yellow-900' :
                    idx === 1 ? 'bg-gray-300 text-gray-900' :
                    idx === 2 ? 'bg-orange-400 text-orange-900' :
                    'bg-primary-100 text-primary-900'
                  }`}>
                    {idx + 1}
                  </div>
                  
                  <div className="flex-1">
                    <div className="font-semibold">{leader.name}</div>
                    <div className="text-sm text-gray-600">{leader.team}</div>
                  </div>

                  <div className="text-right">
                    <div className="font-bold text-primary-600">{leader.xp} XP</div>
                    <div className="text-sm text-gray-600 flex items-center gap-2">
                      <Flame size={14} className="text-orange-600" />
                      {leader.streak} dias
                    </div>
                  </div>

                  <div className="flex gap-1">
                    {Object.entries(leader.reactions).map(([emoji, count]) => (
                      count > 0 && (
                        <span key={emoji} className="text-xs px-2 py-1 bg-white rounded">
                          {emoji === 'clap' ? '👏' : emoji === 'fire' ? '🔥' : emoji === 'flex' ? '💪' : '😂'} {count}
                        </span>
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
              return (
                <div key={goal.id} className="card">
                  <div className="flex items-start justify-between mb-3">
                    <h3 className="font-semibold">{goal.title}</h3>
                    <div className="w-8 h-8 bg-primary-100 rounded-lg flex items-center justify-center">
                      <Medal className="text-primary-600" size={20} />
                    </div>
                  </div>
                  <p className="text-sm text-gray-600 mb-4">{goal.desc}</p>
                  
                  <div className="mb-2">
                    <div className="flex justify-between text-sm mb-1">
                      <span className="text-gray-600">Progresso</span>
                      <span className="font-medium">{goal.progress} / {goal.target} {goal.unit}</span>
                    </div>
                    <div className="w-full bg-gray-200 rounded-full h-2">
                      <div 
                        className="bg-primary-600 h-2 rounded-full transition-all" 
                        style={{ width: `${Math.min(progress, 100)}%` }}
                      ></div>
                    </div>
                  </div>
                  
                  <div className="text-right text-xs text-gray-500">
                    {Math.round(progress)}% completo
                  </div>
                </div>
              )
            })}
          </div>
        )}

        {activeTab === 'groups' && (
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            {groups.map((group) => (
              <div key={group.id} className="card">
                <div className="flex items-start justify-between mb-3">
                  <h3 className="font-semibold text-lg">{group.name}</h3>
                  <span className={`px-2 py-1 rounded text-xs font-medium ${
                    group.open 
                      ? 'bg-green-100 text-green-700' 
                      : 'bg-gray-100 text-gray-700'
                  }`}>
                    {group.open ? 'Aberto' : 'Fechado'}
                  </span>
                </div>
                
                <div className="flex items-center gap-2 text-gray-600 mb-4">
                  <Users size={16} />
                  <span className="text-sm">{group.members} membros</span>
                </div>

                {group.open && (
                  <button className="w-full btn-primary">
                    Participar
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

