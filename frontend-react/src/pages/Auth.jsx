import { useState } from 'react'
import { useAuth } from '../context/AuthContext'
import { useNavigate } from 'react-router-dom'
import { Mail, Lock, User, GraduationCap, Brain, Eye, EyeOff, CheckCircle, Star, Zap, Flame, ArrowRight, Sparkles, Shield, Globe, Award, TrendingUp } from 'lucide-react'

export default function Auth() {
  const [isLogin, setIsLogin] = useState(true)
  const [email, setEmail] = useState('demo@lingu.com')
  const [password, setPassword] = useState('demo12345')
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
        const result = await login(email, password)
        console.log('Login successful:', result)
        navigate('/dashboard')
      } else {
        const result = await register(email, name, password, cefrLevel)
        console.log('Registration successful:', result)
        navigate('/dashboard')
      }
    } catch (err) {
      console.error('Auth error:', err)
      setError(err.message || 'Erro ao fazer login/registro')
    } finally {
      setLoading(false)
    }
  }

  const features = [
    { icon: Brain, text: "IA Avançada", color: "from-blue-500 to-cyan-500" },
    { icon: Zap, text: "Aprendizado Rápido", color: "from-blue-600 to-blue-800" },
    { icon: Award, text: "Gamificação", color: "from-cyan-500 to-blue-500" },
    { icon: Shield, text: "100% Seguro", color: "from-blue-700 to-blue-900" }
  ]

  return (
    <div className="min-h-screen bg-gradient-to-br from-slate-900 via-blue-900 to-blue-800 relative overflow-hidden">
      {/* Animated Background */}
      <div className="absolute inset-0 overflow-hidden">
        <div className="absolute -top-40 -right-40 w-80 h-80 bg-blue-500 rounded-full mix-blend-multiply filter blur-xl opacity-20 animate-pulse"></div>
        <div className="absolute -bottom-40 -left-40 w-80 h-80 bg-cyan-500 rounded-full mix-blend-multiply filter blur-xl opacity-20 animate-pulse" style={{animationDelay: '2s'}}></div>
        <div className="absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 w-80 h-80 bg-blue-600 rounded-full mix-blend-multiply filter blur-xl opacity-20 animate-pulse" style={{animationDelay: '4s'}}></div>
      </div>

      <div className="relative z-10 min-h-screen flex">
        {/* Left Side - Features */}
        <div className="hidden lg:flex lg:w-1/2 flex-col justify-center px-12">
          <div className="max-w-md">
            <div className="mb-8">
              <div className="flex items-center gap-3 mb-6">
                <div className="w-12 h-12 bg-white/10 backdrop-blur-md rounded-2xl flex items-center justify-center border border-white/20">
                  <Brain className="w-8 h-8 text-white" />
                </div>
                <span className="text-3xl font-bold text-white">AI Linguo</span>
              </div>
              <h1 className="text-5xl font-black text-white mb-6 leading-tight">
                Domine o <span className="bg-gradient-to-r from-pink-400 via-purple-400 to-indigo-400 bg-clip-text text-transparent">Inglês</span> com IA
              </h1>
              <p className="text-xl text-white/80 leading-relaxed">
                A plataforma mais avançada para aprender inglês. Conversas reais, correções instantâneas e gamificação que te mantém motivado.
              </p>
            </div>

            <div className="grid grid-cols-2 gap-4">
              {features.map((feature, index) => (
                <div key={index} className="bg-white/10 backdrop-blur-md border border-white/20 rounded-2xl p-4 hover:bg-white/20 transition-all duration-300">
                  <div className={`w-12 h-12 bg-gradient-to-r ${feature.color} rounded-xl flex items-center justify-center mb-3`}>
                    <feature.icon className="w-6 h-6 text-white" />
                  </div>
                  <div className="text-white font-semibold">{feature.text}</div>
                </div>
              ))}
            </div>

            <div className="mt-8 flex items-center gap-4 text-white/70">
              <div className="flex items-center gap-2">
                <CheckCircle className="w-5 h-5 text-green-400" />
                <span>10K+ Estudantes</span>
              </div>
              <div className="flex items-center gap-2">
                <Star className="w-5 h-5 text-yellow-400" />
                <span>4.9★ Avaliação</span>
              </div>
            </div>
          </div>
        </div>

        {/* Right Side - Auth Form */}
        <div className="w-full lg:w-1/2 flex items-center justify-center px-4 py-12">
          <div className="w-full max-w-md">
            {/* Mobile Logo */}
            <div className="lg:hidden flex items-center gap-3 mb-8 justify-center">
              <div className="w-12 h-12 bg-white/10 backdrop-blur-md rounded-2xl flex items-center justify-center border border-white/20">
                <Brain className="w-8 h-8 text-white" />
              </div>
              <span className="text-2xl font-bold text-white">AI Linguo</span>
            </div>

            {/* Auth Card */}
            <div className="bg-white/10 backdrop-blur-md border border-white/20 rounded-3xl p-8 shadow-2xl">
              {/* Toggle Buttons */}
              <div className="flex bg-white/5 rounded-2xl p-1 mb-8">
                <button
                  onClick={() => setIsLogin(true)}
                  className={`flex-1 py-3 px-4 rounded-xl font-semibold transition-all duration-300 ${
                    isLogin 
                      ? 'bg-gradient-to-r from-pink-500 to-purple-500 text-white shadow-lg' 
                      : 'text-white/70 hover:text-white'
                  }`}
                >
                  Entrar
                </button>
                <button
                  onClick={() => setIsLogin(false)}
                  className={`flex-1 py-3 px-4 rounded-xl font-semibold transition-all duration-300 ${
                    !isLogin 
                      ? 'bg-gradient-to-r from-pink-500 to-purple-500 text-white shadow-lg' 
                      : 'text-white/70 hover:text-white'
                  }`}
                >
                  Criar Conta
                </button>
              </div>

              {/* Form */}
              <form onSubmit={handleSubmit} className="space-y-6">
                {!isLogin && (
                  <div>
                    <label className="block text-white/80 font-semibold mb-2">Nome Completo</label>
                    <div className="relative">
                      <User className="absolute left-4 top-1/2 transform -translate-y-1/2 w-5 h-5 text-white/50" />
                      <input
                        type="text"
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                        className="w-full pl-12 pr-4 py-4 bg-white/10 border border-white/20 rounded-2xl text-white placeholder-white/50 focus:outline-none focus:ring-2 focus:ring-purple-500 focus:border-transparent transition-all duration-300"
                        placeholder="Seu nome completo"
                        required={!isLogin}
                      />
                    </div>
                  </div>
                )}

                <div>
                  <label className="block text-white/80 font-semibold mb-2">Email</label>
                  <div className="relative">
                    <Mail className="absolute left-4 top-1/2 transform -translate-y-1/2 w-5 h-5 text-white/50" />
                    <input
                      type="email"
                      value={email}
                      onChange={(e) => setEmail(e.target.value)}
                      className="w-full pl-12 pr-4 py-4 bg-white/10 border border-white/20 rounded-2xl text-white placeholder-white/50 focus:outline-none focus:ring-2 focus:ring-purple-500 focus:border-transparent transition-all duration-300"
                      placeholder="seu@email.com"
                      required
                    />
                  </div>
                </div>

                <div>
                  <label className="block text-white/80 font-semibold mb-2">Senha</label>
                  <div className="relative">
                    <Lock className="absolute left-4 top-1/2 transform -translate-y-1/2 w-5 h-5 text-white/50" />
                    <input
                      type={showPassword ? 'text' : 'password'}
                      value={password}
                      onChange={(e) => setPassword(e.target.value)}
                      className="w-full pl-12 pr-12 py-4 bg-white/10 border border-white/20 rounded-2xl text-white placeholder-white/50 focus:outline-none focus:ring-2 focus:ring-purple-500 focus:border-transparent transition-all duration-300"
                      placeholder="Sua senha"
                      required
                    />
                    <button
                      type="button"
                      onClick={() => setShowPassword(!showPassword)}
                      className="absolute right-4 top-1/2 transform -translate-y-1/2 text-white/50 hover:text-white transition-colors"
                    >
                      {showPassword ? <EyeOff className="w-5 h-5" /> : <Eye className="w-5 h-5" />}
                    </button>
                  </div>
                </div>

                {!isLogin && (
                  <div>
                    <label className="block text-white/80 font-semibold mb-2">Nível de Inglês</label>
                    <div className="relative">
                      <GraduationCap className="absolute left-4 top-1/2 transform -translate-y-1/2 w-5 h-5 text-white/50" />
                      <select
                        value={cefrLevel}
                        onChange={(e) => setCefrLevel(e.target.value)}
                        className="w-full pl-12 pr-4 py-4 bg-white/10 border border-white/20 rounded-2xl text-white focus:outline-none focus:ring-2 focus:ring-purple-500 focus:border-transparent transition-all duration-300 appearance-none"
                        required={!isLogin}
                      >
                        <option value="A1" className="bg-gray-800">A1 - Iniciante</option>
                        <option value="A2" className="bg-gray-800">A2 - Básico</option>
                        <option value="B1" className="bg-gray-800">B1 - Intermediário</option>
                        <option value="B2" className="bg-gray-800">B2 - Intermediário Avançado</option>
                        <option value="C1" className="bg-gray-800">C1 - Avançado</option>
                        <option value="C2" className="bg-gray-800">C2 - Fluente</option>
                      </select>
                    </div>
                  </div>
                )}

                {error && (
                  <div className="bg-red-500/20 border border-red-500/30 rounded-2xl p-4 text-red-200 text-center">
                    {error}
                  </div>
                )}

                <button
                  type="submit"
                  disabled={loading}
                  className="w-full bg-gradient-to-r from-pink-500 via-purple-500 to-indigo-500 text-white font-bold text-lg py-4 rounded-2xl hover:scale-105 transition-all duration-300 shadow-lg hover:shadow-xl disabled:opacity-50 disabled:cursor-not-allowed disabled:hover:scale-100 flex items-center justify-center gap-3"
                >
                  {loading ? (
                    <div className="flex items-center gap-3">
                      <div className="animate-spin rounded-full h-5 w-5 border-2 border-white border-t-transparent"></div>
                      Carregando...
                    </div>
                  ) : (
                    <>
                      {isLogin ? 'Entrar' : 'Criar Conta'}
                      <ArrowRight className="w-5 h-5" />
                    </>
                  )}
                </button>
              </form>

              {/* Demo Credentials */}
              <div className="mt-6 p-4 bg-white/5 rounded-2xl border border-white/10">
                <div className="text-white/70 text-sm text-center mb-2">Credenciais de Demo:</div>
                <div className="text-white/50 text-xs text-center">
                  Email: demo@lingu.com<br />
                  Senha: demo12345
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}