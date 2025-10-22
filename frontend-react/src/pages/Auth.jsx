import { useState } from 'react'
import { useAuth } from '../context/AuthContext'
import { useNavigate } from 'react-router-dom'
import { Mail, Lock, User, GraduationCap, Brain, Eye, EyeOff, CheckCircle, Star, Zap, Flame } from 'lucide-react'

export default function Auth() {
  const [isLogin, setIsLogin] = useState(true)
  const [email, setEmail] = useState('admin@lingu.com')
  const [password, setPassword] = useState('admin123')
  const [name, setName] = useState('')
  const [cefrLevel, setCefrLevel] = useState('A2')
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(false)
  const [showPassword, setShowPassword] = useState(false)

  const { login, register } = useAuth()
  const navigate = useNavigate()

  const handleSubmit = async (e) => {
    e.preventDefault()
    setError('')
    setLoading(true)

    try {
      if (isLogin) {
        await login(email, password)
      } else {
        await register(email, name, password, cefrLevel)
      }
      navigate('/dashboard')
    } catch (err) {
      setError(err.response?.data?.error || 'Erro ao autenticar. Tente novamente.')
    } finally {
      setLoading(false)
    }
  }

  const features = [
    { icon: Brain, text: "Tutor IA Avançado" },
    { icon: Zap, text: "Correções em Tempo Real" },
    { icon: Flame, text: "Gamificação" },
    { icon: Star, text: "Progresso Personalizado" }
  ]

  return (
    <div className="min-h-screen bg-gradient-to-br from-slate-50 to-blue-50 flex items-center justify-center px-4">
      <div className="max-w-6xl w-full grid lg:grid-cols-2 gap-12 items-center">
        {/* Left Side - Features */}
        <div className="hidden lg:block">
          <div className="mb-8">
            <div className="flex items-center gap-3 mb-6">
              <div className="w-12 h-12 bg-gradient-to-r from-blue-500 to-blue-600 rounded-2xl flex items-center justify-center">
                <Brain className="text-white" size={28} />
              </div>
              <h1 className="text-4xl font-bold text-gradient">Linguo</h1>
            </div>
            <h2 className="text-3xl font-bold text-gray-900 mb-4">
              Aprenda Inglês de{' '}
              <span className="text-gradient">Forma Divertida</span>
            </h2>
            <p className="text-xl text-gray-600 mb-8">
              Seu companheiro de estudos disponível 24/7. Aprenda com conversação, 
              exercícios personalizados e acompanhe seu progresso de forma gamificada.
            </p>
          </div>

          <div className="space-y-6">
            {features.map((feature, index) => {
              const Icon = feature.icon
              return (
                <div key={index} className="flex items-center gap-4">
                  <div className="w-12 h-12 bg-gradient-to-r from-blue-500 to-blue-600 rounded-2xl flex items-center justify-center">
                    <Icon className="text-white" size={24} />
                  </div>
                  <div>
                    <h3 className="font-bold text-lg text-gray-900">{feature.text}</h3>
                    <p className="text-gray-600">
                      {index === 0 && "Conversação natural com correções inteligentes"}
                      {index === 1 && "Feedback instantâneo para melhorar rapidamente"}
                      {index === 2 && "Metas, streaks e ranking para manter a motivação"}
                      {index === 3 && "Acompanhe sua evolução com dados precisos"}
                    </p>
                  </div>
                </div>
              )
            })}
          </div>

          <div className="mt-12 bg-gradient-to-r from-blue-500 to-blue-600 rounded-3xl p-8 text-white">
            <h3 className="text-2xl font-bold mb-4">Junte-se a mais de 1 milhão de estudantes!</h3>
            <div className="grid grid-cols-2 gap-6">
              <div className="text-center">
                <div className="text-3xl font-bold">1M+</div>
                <div className="text-sm opacity-90">Estudantes Ativos</div>
              </div>
              <div className="text-center">
                <div className="text-3xl font-bold">95%</div>
                <div className="text-sm opacity-90">Taxa de Satisfação</div>
              </div>
            </div>
          </div>
        </div>

        {/* Right Side - Auth Form */}
        <div className="w-full max-w-md mx-auto">
          <div className="text-center mb-8 lg:hidden">
            <div className="flex items-center justify-center gap-3 mb-4">
              <div className="w-10 h-10 bg-gradient-to-r from-blue-500 to-blue-600 rounded-2xl flex items-center justify-center">
                <Brain className="text-white" size={24} />
              </div>
              <h1 className="text-3xl font-bold text-gradient">Linguo</h1>
            </div>
          </div>

          <div className="card">
            <div className="text-center mb-8">
              <h2 className="text-2xl font-bold text-gray-900 mb-2">
                {isLogin ? 'Bem-vindo de volta!' : 'Crie sua conta'}
              </h2>
              <p className="text-gray-600">
                {isLogin ? 'Entre na sua conta para continuar aprendendo' : 'Comece sua jornada de aprendizado'}
              </p>
            </div>

            <form onSubmit={handleSubmit} className="space-y-6">
              {!isLogin && (
                <div>
                  <label className="block text-sm font-semibold text-gray-700 mb-2">
                    <User size={16} className="inline mr-2" />
                    Nome Completo
                  </label>
                  <input
                    type="text"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                    className="input-field"
                    required
                    minLength={2}
                    placeholder="Digite seu nome completo"
                  />
                </div>
              )}

              <div>
                <label className="block text-sm font-semibold text-gray-700 mb-2">
                  <Mail size={16} className="inline mr-2" />
                  Email
                </label>
                <input
                  type="email"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  className="input-field"
                  required
                  placeholder="seu@email.com"
                />
              </div>

              <div>
                <label className="block text-sm font-semibold text-gray-700 mb-2">
                  <Lock size={16} className="inline mr-2" />
                  Senha
                </label>
                <div className="relative">
                  <input
                    type={showPassword ? "text" : "password"}
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    className="input-field pr-12"
                    required
                    minLength={8}
                    placeholder="Mínimo 8 caracteres"
                  />
                  <button
                    type="button"
                    onClick={() => setShowPassword(!showPassword)}
                    className="absolute right-4 top-1/2 transform -translate-y-1/2 text-gray-400 hover:text-gray-600 transition-colors"
                  >
                    {showPassword ? <EyeOff size={20} /> : <Eye size={20} />}
                  </button>
                </div>
              </div>

              {!isLogin && (
                <div>
                  <label className="block text-sm font-semibold text-gray-700 mb-2">
                    <GraduationCap size={16} className="inline mr-2" />
                    Nível de Inglês (CEFR)
                  </label>
                  <select
                    value={cefrLevel}
                    onChange={(e) => setCefrLevel(e.target.value)}
                    className="input-field"
                  >
                    <option value="A1">A1 - Iniciante</option>
                    <option value="A2">A2 - Básico</option>
                    <option value="B1">B1 - Intermediário</option>
                    <option value="B2">B2 - Intermediário-Avançado</option>
                    <option value="C1">C1 - Avançado</option>
                  </select>
                </div>
              )}

              {error && (
                <div className="bg-red-50 border-2 border-red-200 text-red-700 px-4 py-3 rounded-2xl text-sm flex items-center gap-2">
                  <div className="w-5 h-5 bg-red-200 rounded-full flex items-center justify-center flex-shrink-0">
                    <span className="text-red-600 text-xs">!</span>
                  </div>
                  {error}
                </div>
              )}

              <button
                type="submit"
                disabled={loading}
                className="w-full btn-primary text-lg py-4"
              >
                {loading ? (
                  <div className="flex items-center justify-center gap-2">
                    <div className="animate-spin rounded-full h-5 w-5 border-2 border-white border-t-transparent"></div>
                    Carregando...
                  </div>
                ) : (
                  isLogin ? 'Entrar' : 'Criar Conta'
                )}
              </button>
            </form>

            <div className="mt-8 text-center">
              <button
                onClick={() => setIsLogin(!isLogin)}
                className="text-blue-600 hover:text-blue-700 font-semibold transition-colors"
              >
                {isLogin ? 'Não tem conta? Criar conta' : 'Já tem conta? Fazer login'}
              </button>
            </div>

            {/* Test Credentials */}
            {isLogin && (
              <div className="mt-6 p-4 bg-blue-50 border-2 border-blue-200 rounded-2xl">
                <h3 className="font-bold text-blue-800 mb-2">🔑 Credenciais de Teste</h3>
                <div className="text-sm text-blue-700 space-y-1">
                  <div><strong>Admin:</strong> admin@lingu.com / admin123</div>
                  <div><strong>Usuário:</strong> test@lingu.com / admin123</div>
                </div>
              </div>
            )}

            {/* Benefits */}
            {!isLogin && (
              <div className="mt-8 pt-6 border-t border-gray-200">
                <div className="text-center mb-4">
                  <span className="text-sm text-gray-600">Ao criar uma conta, você ganha:</span>
                </div>
                <div className="grid grid-cols-2 gap-3 text-sm">
                  <div className="flex items-center gap-2 text-green-600">
                    <CheckCircle size={16} />
                    <span>Acesso gratuito</span>
                  </div>
                  <div className="flex items-center gap-2 text-green-600">
                    <CheckCircle size={16} />
                    <span>Tutor IA 24/7</span>
                  </div>
                  <div className="flex items-center gap-2 text-green-600">
                    <CheckCircle size={16} />
                    <span>Progresso salvo</span>
                  </div>
                  <div className="flex items-center gap-2 text-green-600">
                    <CheckCircle size={16} />
                    <span>Sem anúncios</span>
                  </div>
                </div>
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  )
}

