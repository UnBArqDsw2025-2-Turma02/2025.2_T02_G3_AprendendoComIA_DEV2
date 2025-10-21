import { useState, useEffect } from 'react'
import { useAuth } from '../context/AuthContext'
import Navbar from '../components/Navbar'
import axios from 'axios'
import { BookOpen, Check, X, RotateCcw } from 'lucide-react'

export default function Vocabulary() {
  const { user } = useAuth()
  const [cards, setCards] = useState([])
  const [currentIndex, setCurrentIndex] = useState(0)
  const [showAnswer, setShowAnswer] = useState(false)
  const [loading, setLoading] = useState(true)

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
      
      <main className="max-w-3xl mx-auto px-4 py-8">
        <div className="mb-6">
          <h1 className="text-3xl font-bold text-gray-900">Vocabulário</h1>
          <p className="text-gray-600 mt-1">Revise suas palavras com repetição espaçada</p>
        </div>

        {cards.length === 0 ? (
          <div className="card text-center py-12">
            <BookOpen size={64} className="mx-auto text-gray-400 mb-4" />
            <h2 className="text-xl font-semibold text-gray-700 mb-2">
              Nenhum cartão para revisar!
            </h2>
            <p className="text-gray-600">Volte mais tarde para revisar novos cartões.</p>
          </div>
        ) : currentIndex >= cards.length ? (
          <div className="card text-center py-12">
            <Check size={64} className="mx-auto text-green-600 mb-4" />
            <h2 className="text-xl font-semibold text-gray-700 mb-2">
              Parabéns! Você revisou todos os cartões!
            </h2>
            <p className="text-gray-600 mb-6">Volte amanhã para revisar novamente.</p>
            <button onClick={loadCards} className="btn-primary">
              <RotateCcw size={20} className="inline mr-2" />
              Recarregar
            </button>
          </div>
        ) : (
          <div>
            <div className="mb-4 text-sm text-gray-600 text-center">
              Cartão {currentIndex + 1} de {cards.length}
            </div>

            <div className="card mb-6">
              <div className="text-center py-12">
                <div className="text-4xl font-bold text-primary-600 mb-4">
                  {currentCard.term}
                </div>

                {showAnswer && (
                  <div className="space-y-4 animate-fade-in">
                    <div className="text-2xl text-gray-700 mb-2">
                      {currentCard.meaning}
                    </div>
                    <div className="text-gray-600 italic">
                      "{currentCard.example}"
                    </div>
                    <div className="inline-block px-3 py-1 bg-primary-100 text-primary-700 rounded-full text-sm">
                      Nível: {currentCard.cefrLevel}
                    </div>
                  </div>
                )}
              </div>
            </div>

            {!showAnswer ? (
              <div className="flex justify-center">
                <button
                  onClick={() => setShowAnswer(true)}
                  className="btn-primary px-8 py-3"
                >
                  Mostrar Resposta
                </button>
              </div>
            ) : (
              <div className="grid grid-cols-2 md:grid-cols-4 gap-3">
                <button
                  onClick={() => reviewCard('again')}
                  className="btn-secondary flex flex-col items-center gap-2 py-4"
                >
                  <X size={24} className="text-red-600" />
                  <span className="text-sm">Não lembro</span>
                </button>

                <button
                  onClick={() => reviewCard('hard')}
                  className="btn-secondary flex flex-col items-center gap-2 py-4"
                >
                  <span className="text-2xl">😕</span>
                  <span className="text-sm">Difícil</span>
                </button>

                <button
                  onClick={() => reviewCard('good')}
                  className="btn-secondary flex flex-col items-center gap-2 py-4"
                >
                  <span className="text-2xl">🙂</span>
                  <span className="text-sm">Bom</span>
                </button>

                <button
                  onClick={() => reviewCard('easy')}
                  className="btn-primary flex flex-col items-center gap-2 py-4"
                >
                  <Check size={24} />
                  <span className="text-sm">Fácil</span>
                </button>
              </div>
            )}
          </div>
        )}
      </main>
    </div>
  )
}

