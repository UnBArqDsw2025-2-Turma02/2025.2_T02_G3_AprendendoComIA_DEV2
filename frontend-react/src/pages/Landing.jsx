import { Link } from 'react-router-dom'
import { Brain, MessageSquare, BookOpen, Trophy, ArrowRight, Star, Users, Zap, Shield, Globe } from 'lucide-react'

export default function Landing() {
  return (
    <div className="min-h-screen bg-gradient-to-br from-slate-50 to-blue-50">
      {/* Navigation */}
      <nav className="navbar">
        <div className="container mx-auto px-4 py-4">
          <div className="flex justify-between items-center">
            <div className="flex items-center gap-3">
              <div className="w-10 h-10 bg-gradient-to-r from-blue-500 to-blue-600 rounded-2xl flex items-center justify-center">
                <BookOpen className="text-white" size={24} />
              </div>
              <h1 className="text-2xl font-bold text-gradient">Linguo</h1>
            </div>
            <div className="flex items-center gap-4">
              <Link to="/auth" className="btn-outline">
                Entrar
              </Link>
              <Link to="/auth" className="btn-primary">
                Começar Grátis
              </Link>
            </div>
          </div>
        </div>
      </nav>

      {/* Hero Section */}
      <main className="container mx-auto px-4 py-16">
        <div className="max-w-6xl mx-auto">
          <div className="text-center mb-16">
            <div className="inline-flex items-center gap-2 bg-blue-100 text-blue-800 px-4 py-2 rounded-full text-sm font-semibold mb-6 bounce-in">
              <Star className="w-4 h-4" />
              #1 App de Inglês Interativo
            </div>
            
            <h1 className="text-6xl md:text-7xl font-bold text-gray-900 mb-6 leading-tight fade-in">
              Aprenda Inglês de{' '}
              <span className="text-gradient">Forma Divertida</span>
            </h1>
            
            <p className="text-xl md:text-2xl text-gray-600 mb-12 max-w-3xl mx-auto leading-relaxed slide-up">
              Seu companheiro de estudos disponível 24/7. Aprenda com conversação, 
              exercícios personalizados e acompanhe seu progresso de forma gamificada.
            </p>

            <div className="flex flex-col sm:flex-row gap-4 justify-center mb-16">
              <Link to="/auth" className="btn-primary flex items-center justify-center gap-2 text-xl px-8 py-4">
                Começar Grátis <ArrowRight size={24} />
              </Link>
              <button className="btn-secondary flex items-center justify-center gap-2 text-xl px-8 py-4">
                <Globe size={24} />
                Ver Demo
              </button>
            </div>

            {/* Stats */}
            <div className="grid grid-cols-1 md:grid-cols-3 gap-8 mb-20">
              <div className="text-center pulse-gentle">
                <div className="text-4xl font-bold text-blue-600 mb-2">1M+</div>
                <div className="text-gray-600">Estudantes Ativos</div>
              </div>
              <div className="text-center pulse-gentle" style={{animationDelay: '0.5s'}}>
                <div className="text-4xl font-bold text-blue-600 mb-2">95%</div>
                <div className="text-gray-600">Taxa de Satisfação</div>
              </div>
              <div className="text-center pulse-gentle" style={{animationDelay: '1s'}}>
                <div className="text-4xl font-bold text-blue-600 mb-2">24/7</div>
                <div className="text-gray-600">Disponibilidade</div>
              </div>
            </div>
          </div>

          {/* Features Grid */}
          <div className="grid md:grid-cols-2 lg:grid-cols-4 gap-8 mb-20">
            <div className="card-hover text-center group fade-in">
              <div className="w-16 h-16 bg-gradient-to-r from-blue-500 to-blue-600 rounded-3xl flex items-center justify-center mx-auto mb-6 group-hover:scale-110 transition-transform duration-300 float">
                <MessageSquare className="text-white" size={32} />
              </div>
              <h3 className="text-xl font-bold mb-4">Conversação Interativa</h3>
              <p className="text-gray-600 leading-relaxed">
                Pratique conversação com feedback instantâneo e correções em tempo real
              </p>
            </div>

            <div className="card-hover text-center group fade-in" style={{animationDelay: '0.2s'}}>
              <div className="w-16 h-16 bg-gradient-to-r from-purple-500 to-pink-600 rounded-3xl flex items-center justify-center mx-auto mb-6 group-hover:scale-110 transition-transform duration-300 float" style={{animationDelay: '0.5s'}}>
                <BookOpen className="text-white" size={32} />
              </div>
              <h3 className="text-xl font-bold mb-4">Vocabulário Inteligente</h3>
              <p className="text-gray-600 leading-relaxed">
                Memorize palavras com repetição espaçada cientificamente comprovada
              </p>
            </div>

            <div className="card-hover text-center group fade-in" style={{animationDelay: '0.4s'}}>
              <div className="w-16 h-16 bg-gradient-to-r from-yellow-500 to-orange-600 rounded-3xl flex items-center justify-center mx-auto mb-6 group-hover:scale-110 transition-transform duration-300 float" style={{animationDelay: '1s'}}>
                <Trophy className="text-white" size={32} />
              </div>
              <h3 className="text-xl font-bold mb-4">Gamificação</h3>
              <p className="text-gray-600 leading-relaxed">
                Metas, streaks e ranking para manter a motivação
              </p>
            </div>

            <div className="card-hover text-center group fade-in" style={{animationDelay: '0.6s'}}>
              <div className="w-16 h-16 bg-gradient-to-r from-green-500 to-emerald-600 rounded-3xl flex items-center justify-center mx-auto mb-6 group-hover:scale-110 transition-transform duration-300 float" style={{animationDelay: '1.5s'}}>
                <Zap className="text-white" size={32} />
              </div>
              <h3 className="text-xl font-bold mb-4">Progresso Rápido</h3>
              <p className="text-gray-600 leading-relaxed">
                Acompanhe sua evolução com dados precisos e insights personalizados
              </p>
            </div>
          </div>

          {/* How it Works */}
          <div className="bg-white rounded-3xl shadow-2xl p-12 mb-20 slide-up">
            <div className="text-center mb-12">
              <h2 className="text-4xl font-bold text-gray-900 mb-4">Como Funciona</h2>
              <p className="text-xl text-gray-600">Aprenda inglês de forma eficiente e divertida</p>
            </div>
            
            <div className="grid md:grid-cols-3 gap-8">
              <div className="text-center bounce-in">
                <div className="w-20 h-20 bg-gradient-to-r from-blue-500 to-blue-600 rounded-full flex items-center justify-center mx-auto mb-6 text-white text-2xl font-bold pulse-gentle">
                  1
                </div>
                <h3 className="text-xl font-bold mb-4">Escolha seu Nível</h3>
                <p className="text-gray-600">Teste seu inglês e comece no nível ideal para você</p>
              </div>
              
              <div className="text-center bounce-in" style={{animationDelay: '0.3s'}}>
                <div className="w-20 h-20 bg-gradient-to-r from-purple-500 to-pink-600 rounded-full flex items-center justify-center mx-auto mb-6 text-white text-2xl font-bold pulse-gentle" style={{animationDelay: '0.5s'}}>
                  2
                </div>
                <h3 className="text-xl font-bold mb-4">Pratique Diariamente</h3>
                <p className="text-gray-600">Exercícios personalizados e conversação interativa</p>
              </div>
              
              <div className="text-center bounce-in" style={{animationDelay: '0.6s'}}>
                <div className="w-20 h-20 bg-gradient-to-r from-green-500 to-emerald-600 rounded-full flex items-center justify-center mx-auto mb-6 text-white text-2xl font-bold pulse-gentle" style={{animationDelay: '1s'}}>
                  3
                </div>
                <h3 className="text-xl font-bold mb-4">Veja seu Progresso</h3>
                <p className="text-gray-600">Acompanhe sua evolução e conquiste metas</p>
              </div>
            </div>
          </div>

          {/* CTA Section */}
          <div className="hero-gradient rounded-3xl p-12 text-center text-white fade-in">
            <h2 className="text-4xl font-bold mb-6">Pronto para Começar?</h2>
            <p className="text-xl mb-8 opacity-90">
              Junte-se a mais de 1 milhão de estudantes que já estão aprendendo inglês de forma divertida
            </p>
            <Link to="/auth" className="bg-white text-blue-600 px-8 py-4 rounded-2xl font-bold text-xl hover:bg-gray-100 transition-colors duration-200 inline-flex items-center gap-2 wiggle">
              Começar Agora <ArrowRight size={24} />
            </Link>
          </div>
        </div>
      </main>

      {/* Footer */}
      <footer className="bg-gray-900 text-white py-12">
        <div className="container mx-auto px-4">
          <div className="grid md:grid-cols-4 gap-8">
            <div>
              <div className="flex items-center gap-3 mb-4">
                <div className="w-8 h-8 bg-gradient-to-r from-blue-500 to-blue-600 rounded-xl flex items-center justify-center">
                  <BookOpen className="text-white" size={20} />
                </div>
                <span className="text-xl font-bold">Linguo</span>
              </div>
              <p className="text-gray-400">
                Aprenda inglês de forma eficiente e divertida com tecnologia moderna.
              </p>
            </div>
            
            <div>
              <h3 className="font-bold mb-4">Produto</h3>
              <ul className="space-y-2 text-gray-400">
                <li><a href="#" className="hover:text-white transition-colors">Recursos</a></li>
                <li><a href="#" className="hover:text-white transition-colors">Preços</a></li>
                <li><a href="#" className="hover:text-white transition-colors">API</a></li>
              </ul>
            </div>
            
            <div>
              <h3 className="font-bold mb-4">Suporte</h3>
              <ul className="space-y-2 text-gray-400">
                <li><a href="#" className="hover:text-white transition-colors">Central de Ajuda</a></li>
                <li><a href="#" className="hover:text-white transition-colors">Contato</a></li>
                <li><a href="#" className="hover:text-white transition-colors">Comunidade</a></li>
              </ul>
            </div>
            
            <div>
              <h3 className="font-bold mb-4">Legal</h3>
              <ul className="space-y-2 text-gray-400">
                <li><a href="#" className="hover:text-white transition-colors">Privacidade</a></li>
                <li><a href="#" className="hover:text-white transition-colors">Termos</a></li>
                <li><a href="#" className="hover:text-white transition-colors">Cookies</a></li>
              </ul>
            </div>
          </div>
          
          <div className="border-t border-gray-800 mt-8 pt-8 text-center text-gray-400">
            <p>© 2025 Linguo. Todos os direitos reservados.</p>
          </div>
        </div>
      </footer>
    </div>
  )
}

