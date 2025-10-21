import { Link } from 'react-router-dom'
import { Brain, MessageSquare, BookOpen, Trophy, ArrowRight } from 'lucide-react'

export default function Landing() {
  return (
    <div className="min-h-screen bg-gradient-to-br from-primary-50 to-blue-100">
      <nav className="container mx-auto px-4 py-6">
        <div className="flex justify-between items-center">
          <h1 className="text-3xl font-bold text-primary-600">AI Linguo</h1>
          <Link to="/auth" className="btn-primary">
            Começar Agora
          </Link>
        </div>
      </nav>

      <main className="container mx-auto px-4 py-20">
        <div className="max-w-4xl mx-auto text-center">
          <h2 className="text-5xl md:text-6xl font-bold text-gray-900 mb-6">
            Aprenda Inglês com <span className="text-primary-600">Inteligência Artificial</span>
          </h2>
          <p className="text-xl text-gray-600 mb-12">
            Seu tutor pessoal de inglês disponível 24/7. Correções em tempo real, 
            exercícios personalizados e acompanhamento do seu progresso.
          </p>

          <div className="flex gap-4 justify-center mb-20">
            <Link to="/auth" className="btn-primary flex items-center gap-2 text-lg px-8 py-4">
              Começar Grátis <ArrowRight size={20} />
            </Link>
          </div>

          <div className="grid md:grid-cols-2 lg:grid-cols-4 gap-6 mt-16">
            <div className="card text-center">
              <div className="w-12 h-12 bg-primary-100 rounded-lg flex items-center justify-center mx-auto mb-4">
                <Brain className="text-primary-600" size={24} />
              </div>
              <h3 className="font-semibold mb-2">Tutor IA</h3>
              <p className="text-sm text-gray-600">
                Conversação natural com correções inteligentes
              </p>
            </div>

            <div className="card text-center">
              <div className="w-12 h-12 bg-primary-100 rounded-lg flex items-center justify-center mx-auto mb-4">
                <MessageSquare className="text-primary-600" size={24} />
              </div>
              <h3 className="font-semibold mb-2">Chat Interativo</h3>
              <p className="text-sm text-gray-600">
                Pratique conversação em tempo real
              </p>
            </div>

            <div className="card text-center">
              <div className="w-12 h-12 bg-primary-100 rounded-lg flex items-center justify-center mx-auto mb-4">
                <BookOpen className="text-primary-600" size={24} />
              </div>
              <h3 className="font-semibold mb-2">Vocabulário SRS</h3>
              <p className="text-sm text-gray-600">
                Memorize palavras com repetição espaçada
              </p>
            </div>

            <div className="card text-center">
              <div className="w-12 h-12 bg-primary-100 rounded-lg flex items-center justify-center mx-auto mb-4">
                <Trophy className="text-primary-600" size={24} />
              </div>
              <h3 className="font-semibold mb-2">Gamificação</h3>
              <p className="text-sm text-gray-600">
                Metas, streaks e ranking com outros alunos
              </p>
            </div>
          </div>
        </div>
      </main>

      <footer className="container mx-auto px-4 py-8 text-center text-gray-600">
        <p>© 2025 AI Linguo. Todos os direitos reservados.</p>
      </footer>
    </div>
  )
}

