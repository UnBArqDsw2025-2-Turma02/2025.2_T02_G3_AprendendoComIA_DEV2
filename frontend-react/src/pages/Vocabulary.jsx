import { useState, useEffect } from 'react'
import { useAuth } from '../context/AuthContext'
import Navbar from '../components/Navbar'
import axios from 'axios'
import { BookOpen, Check, X, RotateCcw, Zap, Star, Flame, Target, Clock, Sparkles } from 'lucide-react'

export default function Vocabulary() {
  const { user } = useAuth()
  const [cards, setCards] = useState([])
  const [currentIndex, setCurrentIndex] = useState(0)
  const [showAnswer, setShowAnswer] = useState(false)
  const [loading, setLoading] = useState(true)
  const [xp, setXp] = useState(0)
  const [streak, setStreak] = useState(0)

  useEffect(() => {
    loadCards()
  }, [])

  const loadCards = async () => {
    try {
      const response = await axios.get(`/api/vocabulary/due?userId=${user.id}&limit=10`, {
        withCredentials: true
      })
      setCards(response.data)
    } catch (error) {
      console.error('Error loading cards:', error)
    } finally {
      setLoading(false)
    }
  }

  const reviewCard = async (result) => {
    if (currentIndex >= cards.length) return

    try {
      await axios.post('/api/vocabulary/review', {
        userId: user.id,
        cardId: cards[currentIndex].id || cards[currentIndex]._id,
        result: result.toUpperCase()
      }, { withCredentials: true })

      // Simulate XP gain based on difficulty
      const xpGain = {
        'AGAIN': 5,
        'HARD': 10,
        'GOOD': 15,
        'EASY': 20
      }
      setXp(prev => prev + xpGain[result.toUpperCase()])

      if (currentIndex < cards.length - 1) {
        setCurrentIndex(currentIndex + 1)
        setShowAnswer(false)
      } else {
        // Finished all cards
        setCurrentIndex(cards.length)
      }
    } catch (error) {
      console.error('Error reviewing card:', error)
    }
  }

  const currentCard = cards[currentIndex]

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
      
      <main className="max-w-4xl mx-auto px-4 py-8">
        {/* Header */}
        <div className="mb-8">
          <div className="flex items-center justify-between mb-4">
            <div>
              <h1 className="text-4xl font-bold text-gray-900 mb-2">Vocabulário</h1>
              <p className="text-xl text-gray-600">Revise suas palavras com repetição espaçada</p>
            </div>
            
            {/* Stats */}
            <div className="flex items-center gap-4">
              <div className="flex items-center gap-2 bg-white px-4 py-3 rounded-2xl shadow-lg">
                <Zap className="text-blue-500" size={20} />
                <span className="font-bold text-lg">{xp} XP</span>
              </div>
              <div className="flex items-center gap-2 bg-white px-4 py-3 rounded-2xl shadow-lg">
                <Flame className="streak-fire" size={20} />
                <span className="font-bold text-lg">{streak}</span>
              </div>
            </div>
          </div>

          {/* Progress Bar */}
          {cards.length > 0 && (
            <div className="mb-6">
              <div className="flex justify-between text-sm text-gray-600 mb-2">
                <span>Progresso</span>
                <span>{currentIndex} / {cards.length}</span>
              </div>
              <div className="progress-bar">
                <div 
                  className="progress-fill" 
                  style={{ width: `${(currentIndex / cards.length) * 100}%` }}
                ></div>
              </div>
            </div>
          )}
        </div>

        {cards.length === 0 ? (
          <div className="card text-center py-16">
            <div className="w-24 h-24 bg-gradient-to-r from-green-500 to-emerald-600 rounded-full flex items-center justify-center mx-auto mb-6">
              <BookOpen size={40} className="text-white" />
            </div>
            <h2 className="text-2xl font-bold text-gray-900 mb-4">
              Nenhum cartão para revisar!
            </h2>
            <p className="text-lg text-gray-600 mb-8">Volte mais tarde para revisar novos cartões.</p>
            <button onClick={loadCards} className="btn-primary">
              <RotateCcw size={20} className="inline mr-2" />
              Recarregar
            </button>
          </div>
        ) : currentIndex >= cards.length ? (
          <div className="card text-center py-16">
            <div className="w-24 h-24 bg-gradient-to-r from-green-500 to-emerald-600 rounded-full flex items-center justify-center mx-auto mb-6">
              <Check size={40} className="text-white" />
            </div>
            <h2 className="text-3xl font-bold text-gray-900 mb-4">
              Parabéns! 🎉
            </h2>
            <p className="text-xl text-gray-600 mb-8">Você revisou todos os cartões!</p>
            <div className="bg-green-50 border-2 border-green-200 rounded-2xl p-6 mb-8">
              <div className="flex items-center justify-center gap-4 mb-4">
                <div className="flex items-center gap-2">
                  <Zap className="text-blue-500" size={24} />
                  <span className="text-2xl font-bold text-blue-600">+{xp} XP</span>
                </div>
                <div className="flex items-center gap-2">
                  <Flame className="text-orange-500" size={24} />
                  <span className="text-2xl font-bold text-orange-600">+1 Streak</span>
                </div>
              </div>
              <p className="text-gray-600">Volte amanhã para revisar novamente!</p>
            </div>
            <button onClick={loadCards} className="btn-primary">
              <RotateCcw size={20} className="inline mr-2" />
              Revisar Novamente
            </button>
          </div>
        ) : (
          <div>
            {/* Card */}
            <div className="card mb-8">
              <div className="text-center py-16">
                <div className="flex items-center justify-center gap-4 mb-8">
                  <div className="level-badge">
                    {currentCard.cefrLevel}
                  </div>
                  <div className="flex items-center gap-2 text-gray-500">
                    <Star size={20} />
                    <span>Nível {currentCard.cefrLevel}</span>
                  </div>
                </div>

                <div className="text-6xl font-bold text-gradient mb-8">
                  {currentCard.term}
                </div>

                {showAnswer && (
                  <div className="space-y-6 animate-fade-in">
                    <div className="text-3xl text-gray-700 font-semibold mb-4">
                      {currentCard.meaning}
                    </div>
                    <div className="text-xl text-gray-600 italic bg-gray-50 p-6 rounded-2xl">
                      "{currentCard.example}"
                    </div>
                    <div className="flex items-center justify-center gap-2 text-green-600">
                      <Sparkles size={20} />
                      <span className="font-semibold">Você acertou!</span>
                    </div>
                  </div>
                )}
              </div>
            </div>

            {!showAnswer ? (
              <div className="flex justify-center">
                <button
                  onClick={() => setShowAnswer(true)}
                  className="btn-primary px-12 py-4 text-xl"
                >
                  Mostrar Resposta
                </button>
              </div>
            ) : (
              <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
                <button
                  onClick={() => reviewCard('again')}
                  className="bg-red-50 border-2 border-red-200 text-red-700 hover:bg-red-100 transition-all duration-200 flex flex-col items-center gap-3 py-6 rounded-2xl"
                >
                  <X size={32} className="text-red-600" />
                  <span className="font-semibold">Não lembro</span>
                  <span className="text-sm text-red-500">+5 XP</span>
                </button>

                <button
                  onClick={() => reviewCard('hard')}
                  className="bg-orange-50 border-2 border-orange-200 text-orange-700 hover:bg-orange-100 transition-all duration-200 flex flex-col items-center gap-3 py-6 rounded-2xl"
                >
                  <span className="text-4xl">😕</span>
                  <span className="font-semibold">Difícil</span>
                  <span className="text-sm text-orange-500">+10 XP</span>
                </button>

                <button
                  onClick={() => reviewCard('good')}
                  className="bg-blue-50 border-2 border-blue-200 text-blue-700 hover:bg-blue-100 transition-all duration-200 flex flex-col items-center gap-3 py-6 rounded-2xl"
                >
                  <span className="text-4xl">🙂</span>
                  <span className="font-semibold">Bom</span>
                  <span className="text-sm text-blue-500">+15 XP</span>
                </button>

                <button
                  onClick={() => reviewCard('easy')}
                  className="bg-green-50 border-2 border-green-200 text-green-700 hover:bg-green-100 transition-all duration-200 flex flex-col items-center gap-3 py-6 rounded-2xl"
                >
                  <Check size={32} className="text-green-600" />
                  <span className="font-semibold">Fácil</span>
                  <span className="text-sm text-green-500">+20 XP</span>
                </button>
              </div>
            )}

            {/* Tips */}
            <div className="mt-8 bg-blue-50 border-2 border-blue-200 rounded-2xl p-6">
              <div className="flex items-center gap-2 mb-4">
                <Sparkles className="text-blue-600" size={20} />
                <span className="font-semibold text-blue-800">Dica de Estudo</span>
              </div>
              <p className="text-blue-700">
                {!showAnswer 
                  ? "Tente lembrar o significado antes de ver a resposta. Isso fortalece sua memória!"
                  : "Seja honesto com sua avaliação. Isso ajuda o algoritmo a programar a próxima revisão no momento ideal."
                }
              </p>
            </div>
          </div>
        )}
      </main>
    </div>
  )
}

