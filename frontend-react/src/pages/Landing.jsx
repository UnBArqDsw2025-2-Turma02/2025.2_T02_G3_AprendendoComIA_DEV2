import { Link } from 'react-router-dom'
import { ArrowRight, Star, Globe, Brain, MessageCircle, Award, TrendingUp, Shield, Clock, Zap, Users, Target, BookOpen, Trophy, CheckCircle, Play, Sparkles, Heart, Gem } from 'lucide-react'
import ConversationImage from '../assets/images/conversation.svg'
import VocabularyImage from '../assets/images/vocabulary.svg'
import GamificationImage from '../assets/images/gamification.svg'
import ProgressImage from '../assets/images/progress.svg'

export default function Landing() {
  return (
    <div className="min-h-screen bg-gradient-to-br from-slate-900 via-blue-900 to-blue-800 relative overflow-hidden">
      {/* Animated Background Elements */}
      <div className="absolute inset-0 overflow-hidden">
        <div className="absolute -top-40 -right-40 w-80 h-80 bg-blue-500 rounded-full mix-blend-multiply filter blur-xl opacity-20 animate-pulse"></div>
        <div className="absolute -bottom-40 -left-40 w-80 h-80 bg-cyan-500 rounded-full mix-blend-multiply filter blur-xl opacity-20 animate-pulse" style={{animationDelay: '2s'}}></div>
        <div className="absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 w-80 h-80 bg-blue-500 rounded-full mix-blend-multiply filter blur-xl opacity-20 animate-pulse" style={{animationDelay: '4s'}}></div>
      </div>

      {/* Navigation */}
      <nav className="relative z-10">
        <div className="container mx-auto px-4 py-6">
          <div className="flex justify-between items-center">
            <div className="flex items-center gap-3">
              <div className="w-12 h-12 bg-white/10 backdrop-blur-md rounded-2xl flex items-center justify-center border border-white/20">
                <Brain className="w-8 h-8 text-white" />
              </div>
              <span className="text-2xl font-bold text-white">AI Linguo</span>
            </div>
            
            <div className="flex items-center gap-4">
              <Link 
                to="/auth" 
                className="px-6 py-3 bg-white/10 backdrop-blur-md text-white font-semibold rounded-2xl border border-white/20 hover:bg-white/20 transition-all duration-300 hover:scale-105"
              >
                Entrar
              </Link>
              <Link 
                to="/auth" 
                className="px-6 py-3 bg-gradient-to-r from-blue-500 to-cyan-600 text-white font-semibold rounded-2xl hover:from-blue-600 hover:to-cyan-700 transition-all duration-300 hover:scale-105 shadow-lg hover:shadow-xl"
              >
                Começar Grátis
              </Link>
            </div>
          </div>
        </div>
      </nav>

      {/* Hero Section */}
      <section className="relative z-10 text-center py-20 px-4">
        <div className="container mx-auto max-w-6xl">
          {/* Badge */}
          <div className="inline-flex items-center gap-2 bg-white/10 backdrop-blur-md border border-white/20 rounded-full px-6 py-3 mb-8 hover:bg-white/20 transition-all duration-300">
            <Star className="w-5 h-5 text-yellow-400" />
            <span className="text-white font-semibold">Aprenda Inglês com IA</span>
            <ArrowRight className="w-4 h-4 text-white" />
          </div>

          {/* Main Headline */}
          <h1 className="text-7xl md:text-9xl font-black text-white mb-8 leading-tight">
            Domine o
            <span className="bg-gradient-to-r from-blue-400 via-cyan-400 to-blue-600 bg-clip-text text-transparent"> Inglês</span>
            <br />
            com IA
          </h1>

          {/* Subtitle */}
          <p className="text-xl md:text-2xl text-white/80 mb-12 max-w-4xl mx-auto leading-relaxed">
            A plataforma mais avançada para aprender inglês. Conversas reais, correções instantâneas e gamificação que te mantém motivado.
          </p>

          {/* CTA Buttons */}
          <div className="flex flex-col sm:flex-row gap-6 justify-center items-center mb-16">
            <Link 
              to="/auth" 
              className="group px-12 py-6 bg-gradient-to-r from-blue-500 via-cyan-500 to-blue-600 text-white font-bold text-xl rounded-3xl hover:scale-110 transition-all duration-300 shadow-2xl hover:shadow-blue-500/25 flex items-center gap-3"
            >
              <Play className="w-6 h-6" />
              Começar Agora
              <ArrowRight className="w-6 h-6 group-hover:translate-x-1 transition-transform" />
            </Link>
            <Link 
              to="/auth" 
              className="group px-12 py-6 bg-white/10 backdrop-blur-md text-white font-bold text-xl rounded-3xl border border-white/20 hover:bg-white/20 transition-all duration-300 hover:scale-110 shadow-xl flex items-center gap-3"
            >
              <Globe className="w-6 h-6" />
              Ver Demo
            </Link>
          </div>

          {/* Stats */}
          <div className="grid grid-cols-2 md:grid-cols-4 gap-8 max-w-4xl mx-auto">
            <div className="text-center">
              <div className="text-4xl font-bold text-white mb-2">10K+</div>
              <div className="text-white/70">Estudantes</div>
            </div>
            <div className="text-center">
              <div className="text-4xl font-bold text-white mb-2">95%</div>
              <div className="text-white/70">Taxa de Sucesso</div>
            </div>
            <div className="text-center">
              <div className="text-4xl font-bold text-white mb-2">24/7</div>
              <div className="text-white/70">Disponível</div>
            </div>
            <div className="text-center">
              <div className="text-4xl font-bold text-white mb-2">4.9★</div>
              <div className="text-white/70">Avaliação</div>
            </div>
          </div>
        </div>
      </section>

      {/* Features Section */}
      <section className="relative z-10 py-20 px-4">
        <div className="container mx-auto max-w-6xl">
          <div className="text-center mb-16">
            <h2 className="text-5xl font-bold text-white mb-6">
              Por que escolher o <span className="bg-gradient-to-r from-blue-400 to-cyan-400 bg-clip-text text-transparent">AI Linguo</span>?
            </h2>
            <p className="text-xl text-white/70 max-w-3xl mx-auto">
              Tecnologia de ponta combinada com metodologia comprovada para acelerar seu aprendizado
            </p>
          </div>

          <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-8">
            {/* Feature 1 */}
            <div className="group bg-white/10 backdrop-blur-md border border-white/20 rounded-3xl p-8 hover:bg-white/20 transition-all duration-300 hover:scale-105">
              <div className="w-16 h-16 bg-gradient-to-r from-blue-500 to-cyan-500 rounded-2xl flex items-center justify-center mb-6 group-hover:scale-110 transition-transform">
                <Brain className="w-8 h-8 text-white" />
              </div>
              <h3 className="text-2xl font-bold text-white mb-4">IA Avançada</h3>
              <p className="text-white/70 leading-relaxed">
                Conversas naturais com IA que entende contexto e oferece correções personalizadas em tempo real.
              </p>
            </div>

            {/* Feature 2 */}
            <div className="group bg-white/10 backdrop-blur-md border border-white/20 rounded-3xl p-8 hover:bg-white/20 transition-all duration-300 hover:scale-105">
              <div className="w-16 h-16 bg-gradient-to-r from-blue-500 to-cyan-500 rounded-2xl flex items-center justify-center mb-6 group-hover:scale-110 transition-transform">
                <MessageCircle className="w-8 h-8 text-white" />
              </div>
              <h3 className="text-2xl font-bold text-white mb-4">Conversas Reais</h3>
              <p className="text-white/70 leading-relaxed">
                Pratique com situações do dia a dia. Do trabalho ao lazer, sempre com feedback instantâneo.
              </p>
            </div>

            {/* Feature 3 */}
            <div className="group bg-white/10 backdrop-blur-md border border-white/20 rounded-3xl p-8 hover:bg-white/20 transition-all duration-300 hover:scale-105">
              <div className="w-16 h-16 bg-gradient-to-r from-green-500 to-emerald-500 rounded-2xl flex items-center justify-center mb-6 group-hover:scale-110 transition-transform">
                <Award className="w-8 h-8 text-white" />
              </div>
              <h3 className="text-2xl font-bold text-white mb-4">Gamificação</h3>
              <p className="text-white/70 leading-relaxed">
                Sistema de pontos, níveis e conquistas que transformam o aprendizado em uma jornada viciante.
              </p>
            </div>

            {/* Feature 4 */}
            <div className="group bg-white/10 backdrop-blur-md border border-white/20 rounded-3xl p-8 hover:bg-white/20 transition-all duration-300 hover:scale-105">
              <div className="w-16 h-16 bg-gradient-to-r from-orange-500 to-red-500 rounded-2xl flex items-center justify-center mb-6 group-hover:scale-110 transition-transform">
                <TrendingUp className="w-8 h-8 text-white" />
              </div>
              <h3 className="text-2xl font-bold text-white mb-4">Progresso Rápido</h3>
              <p className="text-white/70 leading-relaxed">
                Algoritmos inteligentes que adaptam o conteúdo ao seu ritmo, garantindo evolução constante.
              </p>
            </div>

            {/* Feature 5 */}
            <div className="group bg-white/10 backdrop-blur-md border border-white/20 rounded-3xl p-8 hover:bg-white/20 transition-all duration-300 hover:scale-105">
              <div className="w-16 h-16 bg-gradient-to-r from-cyan-500 to-blue-500 rounded-2xl flex items-center justify-center mb-6 group-hover:scale-110 transition-transform">
                <Shield className="w-8 h-8 text-white" />
              </div>
              <h3 className="text-2xl font-bold text-white mb-4">100% Seguro</h3>
              <p className="text-white/70 leading-relaxed">
                Seus dados protegidos com criptografia de nível bancário. Aprenda com total privacidade.
              </p>
            </div>

            {/* Feature 6 */}
            <div className="group bg-white/10 backdrop-blur-md border border-white/20 rounded-3xl p-8 hover:bg-white/20 transition-all duration-300 hover:scale-105">
              <div className="w-16 h-16 bg-gradient-to-r from-yellow-500 to-orange-500 rounded-2xl flex items-center justify-center mb-6 group-hover:scale-110 transition-transform">
                <Clock className="w-8 h-8 text-white" />
              </div>
              <h3 className="text-2xl font-bold text-white mb-4">Flexível</h3>
              <p className="text-white/70 leading-relaxed">
                Aprenda quando e onde quiser. Sessões de 5 minutos ou maratonas de estudo, você decide.
              </p>
            </div>
          </div>
        </div>
      </section>

      {/* CTA Section */}
      <section className="relative z-10 py-20 px-4">
        <div className="container mx-auto max-w-4xl text-center">
          <div className="bg-white/10 backdrop-blur-md border border-white/20 rounded-3xl p-12">
            <h2 className="text-5xl font-bold text-white mb-6">
              Pronto para <span className="bg-gradient-to-r from-blue-400 to-cyan-400 bg-clip-text text-transparent">dominar</span> o inglês?
            </h2>
            <p className="text-xl text-white/70 mb-8 max-w-2xl mx-auto">
              Junte-se a milhares de estudantes que já transformaram suas carreiras com o AI Linguo.
            </p>
            <div className="flex flex-col sm:flex-row gap-4 justify-center">
              <Link 
                to="/auth" 
                className="group px-12 py-6 bg-gradient-to-r from-blue-500 via-cyan-500 to-blue-600 text-white font-bold text-xl rounded-3xl hover:scale-110 transition-all duration-300 shadow-2xl hover:shadow-blue-500/25 flex items-center justify-center gap-3"
              >
                <Sparkles className="w-6 h-6" />
                Começar Agora - É Grátis!
                <ArrowRight className="w-6 h-6 group-hover:translate-x-1 transition-transform" />
              </Link>
            </div>
            <p className="text-white/50 text-sm mt-4">
              Sem cartão de crédito • Cancelamento a qualquer momento
            </p>
          </div>
        </div>
      </section>

      {/* Footer */}
      <footer className="relative z-10 bg-gradient-to-r from-slate-900 via-blue-900 to-slate-900 border-t border-white/10">
        <div className="container mx-auto max-w-7xl px-4 py-16">
          {/* Main Footer Content */}
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-12 mb-12">
            {/* Brand Section */}
            <div className="lg:col-span-2">
              <div className="flex items-center gap-3 mb-6">
                <div className="w-12 h-12 bg-white/10 backdrop-blur-md rounded-2xl flex items-center justify-center border border-white/20">
                  <Brain className="w-8 h-8 text-white" />
                </div>
                <span className="text-2xl font-bold text-white">AI Linguo</span>
              </div>
              <p className="text-white/70 text-lg mb-6 max-w-md leading-relaxed">
                A plataforma mais avançada para aprender inglês com inteligência artificial. 
                Transforme sua carreira dominando o idioma mais importante do mundo.
              </p>
              <div className="flex gap-4">
                <div className="w-10 h-10 bg-white/10 backdrop-blur-md rounded-xl flex items-center justify-center border border-white/20 hover:bg-white/20 transition-all duration-300 cursor-pointer">
                  <Globe className="w-5 h-5 text-white" />
                </div>
                <div className="w-10 h-10 bg-white/10 backdrop-blur-md rounded-xl flex items-center justify-center border border-white/20 hover:bg-white/20 transition-all duration-300 cursor-pointer">
                  <Users className="w-5 h-5 text-white" />
                </div>
                <div className="w-10 h-10 bg-white/10 backdrop-blur-md rounded-xl flex items-center justify-center border border-white/20 hover:bg-white/20 transition-all duration-300 cursor-pointer">
                  <MessageCircle className="w-5 h-5 text-white" />
                </div>
              </div>
            </div>

            {/* Product Links */}
            <div>
              <h3 className="text-xl font-bold text-white mb-6">Produto</h3>
              <ul className="space-y-4">
                <li>
                  <Link to="/auth" className="text-white/70 hover:text-white transition-colors duration-300 flex items-center gap-2">
                    <ArrowRight className="w-4 h-4" />
                    Começar Agora
                  </Link>
                </li>
                <li>
                  <Link to="/auth" className="text-white/70 hover:text-white transition-colors duration-300 flex items-center gap-2">
                    <Play className="w-4 h-4" />
                    Demo Interativo
                  </Link>
                </li>
                <li>
                  <Link to="/auth" className="text-white/70 hover:text-white transition-colors duration-300 flex items-center gap-2">
                    <BookOpen className="w-4 h-4" />
                    Recursos
                  </Link>
                </li>
                <li>
                  <Link to="/auth" className="text-white/70 hover:text-white transition-colors duration-300 flex items-center gap-2">
                    <Trophy className="w-4 h-4" />
                    Preços
                  </Link>
                </li>
              </ul>
            </div>

            {/* Support Links */}
            <div>
              <h3 className="text-xl font-bold text-white mb-6">Suporte</h3>
              <ul className="space-y-4">
                <li>
                  <Link to="/auth" className="text-white/70 hover:text-white transition-colors duration-300 flex items-center gap-2">
                    <Shield className="w-4 h-4" />
                    Central de Ajuda
                  </Link>
                </li>
                <li>
                  <Link to="/auth" className="text-white/70 hover:text-white transition-colors duration-300 flex items-center gap-2">
                    <MessageCircle className="w-4 h-4" />
                    Contato
                  </Link>
                </li>
                <li>
                  <Link to="/auth" className="text-white/70 hover:text-white transition-colors duration-300 flex items-center gap-2">
                    <BookOpen className="w-4 h-4" />
                    Documentação
                  </Link>
                </li>
                <li>
                  <Link to="/auth" className="text-white/70 hover:text-white transition-colors duration-300 flex items-center gap-2">
                    <Users className="w-4 h-4" />
                    Comunidade
                  </Link>
                </li>
              </ul>
            </div>
          </div>

          {/* Stats Section */}
          <div className="bg-white/5 backdrop-blur-md rounded-3xl p-8 mb-12 border border-white/10">
            <div className="grid grid-cols-2 md:grid-cols-4 gap-8">
              <div className="text-center">
                <div className="text-3xl font-bold text-white mb-2">10K+</div>
                <div className="text-white/70">Estudantes Ativos</div>
              </div>
              <div className="text-center">
                <div className="text-3xl font-bold text-white mb-2">95%</div>
                <div className="text-white/70">Taxa de Sucesso</div>
              </div>
              <div className="text-center">
                <div className="text-3xl font-bold text-white mb-2">24/7</div>
                <div className="text-white/70">Disponibilidade</div>
              </div>
              <div className="text-center">
                <div className="text-3xl font-bold text-white mb-2">4.9★</div>
                <div className="text-white/70">Avaliação Média</div>
              </div>
            </div>
          </div>

          {/* Newsletter Section */}
          <div className="bg-gradient-to-r from-blue-500/20 to-cyan-500/20 backdrop-blur-md rounded-3xl p-8 mb-12 border border-white/20">
            <div className="text-center">
              <h3 className="text-2xl font-bold text-white mb-4">
                Receba dicas exclusivas de inglês
              </h3>
              <p className="text-white/70 mb-6 max-w-2xl mx-auto">
                Inscreva-se em nossa newsletter e receba conteúdo premium, 
                dicas de pronúncia e estratégias de aprendizado direto no seu email.
              </p>
              <div className="flex flex-col sm:flex-row gap-4 max-w-md mx-auto">
                <input 
                  type="email" 
                  placeholder="Seu melhor email" 
                  className="flex-1 px-6 py-4 bg-white/10 backdrop-blur-md border border-white/20 rounded-2xl text-white placeholder-white/50 focus:outline-none focus:ring-2 focus:ring-blue-400 focus:border-transparent"
                />
                <button className="px-8 py-4 bg-gradient-to-r from-blue-500 to-cyan-500 text-white font-bold rounded-2xl hover:from-blue-600 hover:to-cyan-600 transition-all duration-300 hover:scale-105 shadow-lg">
                  Inscrever-se
                </button>
              </div>
            </div>
          </div>

          {/* Bottom Footer */}
          <div className="flex flex-col md:flex-row justify-between items-center pt-8 border-t border-white/10">
            <div className="text-white/50 text-sm mb-4 md:mb-0">
              © 2024 AI Linguo. Todos os direitos reservados.
            </div>
            <div className="flex gap-6 text-sm">
              <Link to="/auth" className="text-white/50 hover:text-white transition-colors">
                Termos de Uso
              </Link>
              <Link to="/auth" className="text-white/50 hover:text-white transition-colors">
                Política de Privacidade
              </Link>
              <Link to="/auth" className="text-white/50 hover:text-white transition-colors">
                Cookies
              </Link>
            </div>
          </div>
        </div>
      </footer>
    </div>
  )
}