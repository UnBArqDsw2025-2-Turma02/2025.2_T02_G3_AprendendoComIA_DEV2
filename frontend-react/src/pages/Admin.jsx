import { useState, useEffect } from 'react'
import { 
  Users, 
  BookOpen, 
  MessageSquare, 
  BarChart3, 
  Settings, 
  Shield, 
  TrendingUp, 
  Activity,
  UserCheck,
  AlertTriangle,
  CheckCircle,
  Clock,
  Star,
  Award,
  Target,
  Zap
} from 'lucide-react'
import axios from 'axios'

export default function Admin() {
  const [activeTab, setActiveTab] = useState('dashboard')
  const [stats, setStats] = useState(null)
  const [users, setUsers] = useState([])
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    loadAdminData()
  }, [])

  const loadAdminData = async () => {
    try {
      const [statsResponse, usersResponse] = await Promise.all([
        axios.get('/api/admin/dashboard', { withCredentials: true }),
        axios.get('/api/admin/users', { withCredentials: true })
      ])
      
      setStats(statsResponse.data)
      setUsers(usersResponse.data)
    } catch (error) {
      console.error('Error loading admin data:', error)
    } finally {
      setLoading(false)
    }
  }

  const mockStats = [
    { title: 'Usuários Ativos', value: '12,543', change: '+12%', icon: Users, color: 'blue' },
    { title: 'Lições Completas', value: '45,231', change: '+8%', icon: BookOpen, color: 'green' },
    { title: 'Conversas Hoje', value: '3,421', change: '+15%', icon: MessageSquare, color: 'purple' },
    { title: 'Taxa de Retenção', value: '87%', change: '+3%', icon: TrendingUp, color: 'orange' }
  ]

  const recentUsers = [
    { id: 1, name: 'Ana Silva', email: 'ana@email.com', level: 'B2', joinDate: '2025-01-15', status: 'active' },
    { id: 2, name: 'Carlos Santos', email: 'carlos@email.com', level: 'A1', joinDate: '2025-01-14', status: 'active' },
    { id: 3, name: 'Maria Costa', email: 'maria@email.com', level: 'C1', joinDate: '2025-01-13', status: 'inactive' },
    { id: 4, name: 'João Oliveira', email: 'joao@email.com', level: 'B1', joinDate: '2025-01-12', status: 'active' }
  ]

  const systemAlerts = [
    { type: 'warning', message: 'Alto uso de CPU no servidor principal', time: '2 min atrás' },
    { type: 'info', message: 'Backup automático concluído com sucesso', time: '1 hora atrás' },
    { type: 'success', message: 'Nova versão do sistema implantada', time: '3 horas atrás' }
  ]

  const getStatusColor = (status) => {
    switch (status) {
      case 'active': return 'text-green-600 bg-green-100'
      case 'inactive': return 'text-red-600 bg-red-100'
      default: return 'text-gray-600 bg-gray-100'
    }
  }

  const getAlertIcon = (type) => {
    switch (type) {
      case 'warning': return <AlertTriangle className="w-5 h-5 text-yellow-500" />
      case 'info': return <Activity className="w-5 h-5 text-blue-500" />
      case 'success': return <CheckCircle className="w-5 h-5 text-green-500" />
      default: return <Clock className="w-5 h-5 text-gray-500" />
    }
  }

  return (
    <div className="min-h-screen bg-gradient-to-br from-slate-50 to-blue-50">
      {/* Header */}
      <div className="bg-white shadow-sm border-b border-gray-200">
        <div className="container mx-auto px-4 py-6">
          <div className="flex items-center justify-between">
            <div className="flex items-center gap-4">
              <div className="w-12 h-12 bg-gradient-to-r from-blue-500 to-blue-600 rounded-2xl flex items-center justify-center">
                <Shield className="text-white" size={24} />
              </div>
              <div>
                <h1 className="text-2xl font-bold text-gray-900">Painel Administrativo</h1>
                <p className="text-gray-600">Gerencie sua plataforma de aprendizado</p>
              </div>
            </div>
            <div className="flex items-center gap-4">
              <button className="btn-outline">
                <Settings size={20} />
                Configurações
              </button>
              <button className="btn-primary">
                <UserCheck size={20} />
                Adicionar Usuário
              </button>
            </div>
          </div>
        </div>
      </div>

      {/* Navigation Tabs */}
      <div className="bg-white border-b border-gray-200">
        <div className="container mx-auto px-4">
          <nav className="flex space-x-8">
            {[
              { id: 'dashboard', label: 'Dashboard', icon: BarChart3 },
              { id: 'users', label: 'Usuários', icon: Users },
              { id: 'content', label: 'Conteúdo', icon: BookOpen },
              { id: 'analytics', label: 'Analytics', icon: TrendingUp },
              { id: 'system', label: 'Sistema', icon: Settings }
            ].map(tab => (
              <button
                key={tab.id}
                onClick={() => setActiveTab(tab.id)}
                className={`flex items-center gap-2 py-4 px-2 border-b-2 font-medium transition-colors ${
                  activeTab === tab.id
                    ? 'border-blue-500 text-blue-600'
                    : 'border-transparent text-gray-500 hover:text-gray-700'
                }`}
              >
                <tab.icon size={20} />
                {tab.label}
              </button>
            ))}
          </nav>
        </div>
      </div>

      {/* Main Content */}
      <div className="container mx-auto px-4 py-8">
        {activeTab === 'dashboard' && (
          <div className="space-y-8">
            {/* Stats Grid */}
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
              {(stats || mockStats).map((stat, index) => (
                <div key={index} className="card fade-in" style={{animationDelay: `${index * 0.1}s`}}>
                  <div className="flex items-center justify-between">
                    <div>
                      <p className="text-sm font-medium text-gray-600 mb-1">{stat.title}</p>
                      <p className="text-3xl font-bold text-gray-900">{stat.value}</p>
                      <p className={`text-sm font-medium ${
                        stat.change.startsWith('+') ? 'text-green-600' : 'text-red-600'
                      }`}>
                        {stat.change} vs mês anterior
                      </p>
                    </div>
                    <div className={`w-12 h-12 bg-gradient-to-r ${
                      stat.color === 'blue' ? 'from-blue-500 to-blue-600' :
                      stat.color === 'green' ? 'from-green-500 to-green-600' :
                      stat.color === 'purple' ? 'from-purple-500 to-purple-600' :
                      'from-orange-500 to-orange-600'
                    } rounded-2xl flex items-center justify-center`}>
                      <stat.icon className="text-white" size={24} />
                    </div>
                  </div>
                </div>
              ))}
            </div>

            {/* Charts and Recent Activity */}
            <div className="grid lg:grid-cols-2 gap-8">
              {/* Chart Placeholder */}
              <div className="card">
                <h3 className="text-xl font-bold text-gray-900 mb-6">Atividade dos Últimos 7 Dias</h3>
                <div className="h-64 bg-gradient-to-br from-blue-50 to-blue-100 rounded-2xl flex items-center justify-center">
                  <div className="text-center">
                    <BarChart3 className="w-16 h-16 text-blue-400 mx-auto mb-4" />
                    <p className="text-gray-600">Gráfico de atividade</p>
                  </div>
                </div>
              </div>

              {/* System Alerts */}
              <div className="card">
                <h3 className="text-xl font-bold text-gray-900 mb-6">Alertas do Sistema</h3>
                <div className="space-y-4">
                  {systemAlerts.map((alert, index) => (
                    <div key={index} className="flex items-start gap-3 p-4 bg-gray-50 rounded-2xl">
                      {getAlertIcon(alert.type)}
                      <div className="flex-1">
                        <p className="text-gray-900 font-medium">{alert.message}</p>
                        <p className="text-sm text-gray-500">{alert.time}</p>
                      </div>
                    </div>
                  ))}
                </div>
              </div>
            </div>
          </div>
        )}

        {activeTab === 'users' && (
          <div className="space-y-8">
            <div className="flex items-center justify-between">
              <h2 className="text-2xl font-bold text-gray-900">Gerenciamento de Usuários</h2>
              <div className="flex items-center gap-4">
                <input
                  type="text"
                  placeholder="Buscar usuários..."
                  className="input-field w-64"
                />
                <button className="btn-primary">
                  <UserCheck size={20} />
                  Novo Usuário
                </button>
              </div>
            </div>

            <div className="card">
              <div className="overflow-x-auto">
                <table className="w-full">
                  <thead>
                    <tr className="border-b border-gray-200">
                      <th className="text-left py-4 px-6 font-semibold text-gray-900">Usuário</th>
                      <th className="text-left py-4 px-6 font-semibold text-gray-900">Nível</th>
                      <th className="text-left py-4 px-6 font-semibold text-gray-900">Data de Cadastro</th>
                      <th className="text-left py-4 px-6 font-semibold text-gray-900">Status</th>
                      <th className="text-left py-4 px-6 font-semibold text-gray-900">Ações</th>
                    </tr>
                  </thead>
                  <tbody>
                    {recentUsers.map((user) => (
                      <tr key={user.id} className="border-b border-gray-100 hover:bg-gray-50">
                        <td className="py-4 px-6">
                          <div>
                            <p className="font-medium text-gray-900">{user.name}</p>
                            <p className="text-sm text-gray-500">{user.email}</p>
                          </div>
                        </td>
                        <td className="py-4 px-6">
                          <span className="level-badge">{user.level}</span>
                        </td>
                        <td className="py-4 px-6 text-gray-600">{user.joinDate}</td>
                        <td className="py-4 px-6">
                          <span className={`px-3 py-1 rounded-full text-sm font-medium ${getStatusColor(user.status)}`}>
                            {user.status === 'active' ? 'Ativo' : 'Inativo'}
                          </span>
                        </td>
                        <td className="py-4 px-6">
                          <div className="flex items-center gap-2">
                            <button className="text-blue-600 hover:text-blue-800">
                              <Settings size={16} />
                            </button>
                            <button className="text-red-600 hover:text-red-800">
                              <AlertTriangle size={16} />
                            </button>
                          </div>
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        )}

        {activeTab === 'content' && (
          <div className="space-y-8">
            <div className="flex items-center justify-between">
              <h2 className="text-2xl font-bold text-gray-900">Gerenciamento de Conteúdo</h2>
              <button className="btn-primary">
                <BookOpen size={20} />
                Adicionar Conteúdo
              </button>
            </div>

            <div className="grid md:grid-cols-3 gap-6">
              {[
                { title: 'Lições Disponíveis', count: 156, icon: BookOpen, color: 'blue' },
                  { title: 'Vocabulário', count: 2341, icon: Target, color: 'green' },
                { title: 'Exercícios', count: 89, icon: Zap, color: 'purple' }
              ].map((item, index) => (
                <div key={index} className="card fade-in" style={{animationDelay: `${index * 0.1}s`}}>
                  <div className="flex items-center justify-between">
                    <div>
                      <p className="text-sm font-medium text-gray-600 mb-1">{item.title}</p>
                      <p className="text-3xl font-bold text-gray-900">{item.count.toLocaleString()}</p>
                    </div>
                    <div className={`w-12 h-12 bg-gradient-to-r ${
                      item.color === 'blue' ? 'from-blue-500 to-blue-600' :
                      item.color === 'green' ? 'from-green-500 to-green-600' :
                      'from-purple-500 to-purple-600'
                    } rounded-2xl flex items-center justify-center`}>
                      <item.icon className="text-white" size={24} />
                    </div>
                  </div>
                </div>
              ))}
            </div>
          </div>
        )}

        {activeTab === 'analytics' && (
          <div className="space-y-8">
            <h2 className="text-2xl font-bold text-gray-900">Analytics e Relatórios</h2>
            
            <div className="grid lg:grid-cols-2 gap-8">
              <div className="card">
                <h3 className="text-xl font-bold text-gray-900 mb-6">Métricas de Engajamento</h3>
                <div className="space-y-4">
                  {[
                    { label: 'Tempo médio de sessão', value: '24 min', progress: 75 },
                    { label: 'Taxa de conclusão de lições', value: '68%', progress: 68 },
                    { label: 'Usuários ativos diários', value: '3,421', progress: 85 }
                  ].map((metric, index) => (
                    <div key={index} className="fade-in" style={{animationDelay: `${index * 0.1}s`}}>
                      <div className="flex justify-between items-center mb-2">
                        <span className="text-sm font-medium text-gray-600">{metric.label}</span>
                        <span className="text-sm font-bold text-gray-900">{metric.value}</span>
                      </div>
                      <div className="progress-bar">
                        <div 
                          className="progress-fill" 
                          style={{ width: `${metric.progress}%` }}
                        />
                      </div>
                    </div>
                  ))}
                </div>
              </div>

              <div className="card">
                <h3 className="text-xl font-bold text-gray-900 mb-6">Top Performers</h3>
                <div className="space-y-4">
                  {[
                      { name: 'Ana Silva', level: 'B2', streak: 45, xp: 2340 },
                      { name: 'Carlos Santos', level: 'A1', streak: 32, xp: 1890 },
                      { name: 'Maria Costa', level: 'C1', streak: 28, xp: 3120 }
                  ].map((user, index) => (
                    <div key={index} className="flex items-center justify-between p-4 bg-gray-50 rounded-2xl fade-in" style={{animationDelay: `${index * 0.1}s`}}>
                      <div className="flex items-center gap-3">
                        <div className="w-10 h-10 bg-gradient-to-r from-blue-500 to-blue-600 rounded-full flex items-center justify-center text-white font-bold">
                          {user.name.charAt(0)}
                        </div>
                        <div>
                          <p className="font-medium text-gray-900">{user.name}</p>
                          <p className="text-sm text-gray-500">{user.level} • {user.streak} dias</p>
                        </div>
                      </div>
                      <div className="text-right">
                        <p className="font-bold text-gray-900">{user.xp.toLocaleString()} XP</p>
                        <div className="flex items-center gap-1">
                          <Star className="w-4 h-4 text-yellow-500" />
                          <span className="text-sm text-gray-500">Top {index + 1}</span>
                        </div>
                      </div>
                    </div>
                  ))}
                </div>
              </div>
            </div>
          </div>
        )}

        {activeTab === 'system' && (
          <div className="space-y-8">
            <h2 className="text-2xl font-bold text-gray-900">Configurações do Sistema</h2>
            
            <div className="grid lg:grid-cols-2 gap-8">
              <div className="card">
                <h3 className="text-xl font-bold text-gray-900 mb-6">Status dos Serviços</h3>
                <div className="space-y-4">
                  {[
                    { service: 'API Principal', status: 'online', uptime: '99.9%' },
                    { service: 'Banco de Dados', status: 'online', uptime: '99.8%' },
                    { service: 'Serviço de IA', status: 'online', uptime: '99.7%' },
                    { service: 'CDN', status: 'online', uptime: '99.9%' }
                  ].map((service, index) => (
                    <div key={index} className="flex items-center justify-between p-4 bg-gray-50 rounded-2xl">
                      <div>
                        <p className="font-medium text-gray-900">{service.service}</p>
                        <p className="text-sm text-gray-500">Uptime: {service.uptime}</p>
                      </div>
                      <div className="flex items-center gap-2">
                        <div className="w-3 h-3 bg-green-500 rounded-full"></div>
                        <span className="text-sm font-medium text-green-600">Online</span>
                      </div>
                    </div>
                  ))}
                </div>
              </div>

              <div className="card">
                <h3 className="text-xl font-bold text-gray-900 mb-6">Configurações Gerais</h3>
                <div className="space-y-6">
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-2">
                      Manutenção Programada
                    </label>
                    <input
                      type="datetime-local"
                      className="input-field"
                      defaultValue="2025-01-25T02:00"
                    />
                  </div>
                  
                  <div>
                    <label className="block text-sm font-medium text-gray-700 mb-2">
                      Limite de Usuários Simultâneos
                    </label>
                    <input
                      type="number"
                      className="input-field"
                      defaultValue="10000"
                    />
                  </div>
                  
                  <div className="flex items-center justify-between">
                    <div>
                      <p className="font-medium text-gray-900">Modo de Desenvolvimento</p>
                      <p className="text-sm text-gray-500">Ativar logs detalhados</p>
                    </div>
                    <button className="relative inline-flex h-6 w-11 items-center rounded-full bg-blue-600 transition-colors">
                      <span className="inline-block h-4 w-4 transform rounded-full bg-white transition-transform translate-x-6" />
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        )}
      </div>
    </div>
  )
}
