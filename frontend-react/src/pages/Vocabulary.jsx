import { useState, useEffect } from 'react'
import { useAuth } from '../context/AuthContext'
import Navbar from '../components/Navbar'
import axios from 'axios'
import { BookOpen, Check, X, RotateCcw, Zap, Star, Flame, Target, Clock, Sparkles, ArrowRight, Play, Lock, RefreshCw, TrendingUp, Award, Brain, MessageCircle, Eye } from 'lucide-react'

export default function Vocabulary() {
  const { user } = useAuth()
  const [cards, setCards] = useState([])
  const [currentIndex, setCurrentIndex] = useState(0)
  const [showAnswer, setShowAnswer] = useState(false)
  const [loading, setLoading] = useState(true)
  const [xp, setXp] = useState(0)
  const [streak, setStreak] = useState(0)
  const [completedCards, setCompletedCards] = useState([])
  const [activeTab, setActiveTab] = useState('review') // 'review', 'new', 'completed'

  useEffect(() => {
    loadUserStats()
    loadCards()
  }, [])

  const loadUserStats = async () => {
    try {
      const response = await axios.get('/api/user/stats', { withCredentials: true })
      setXp(response.data.xp || 0)
      setStreak(response.data.streak || 0)
    } catch (error) {
      console.error('Error loading user stats:', error)
    }
  }

  const loadCards = async () => {
    try {
      setLoading(true)
      const cefrLevel = user?.cefrLevel || 'A1'
      const response = await axios.get(`/api/vocabulary/words/random/${cefrLevel}/10`, {
        withCredentials: true
      })
      setCards(response.data)
    } catch (error) {
      console.error('Error loading cards:', error)
      // Fallback to mock data
      setCards([
        {
          id: 1,
          englishWord: 'hello',
          portugueseTranslation: 'olá',
          phonetic: '/həˈloʊ/',
          definition: 'A greeting used when meeting someone',
          exampleSentence: 'Hello, how are you today?',
          cefrLevel: 'A1',
          xpReward: 5
        },
        {
          id: 2,
          englishWord: 'beautiful',
          portugueseTranslation: 'bonito',
          phonetic: '/ˈbjuːtɪfəl/',
          definition: 'Pleasing to the senses or mind',
          exampleSentence: 'She has a beautiful smile.',
          cefrLevel: 'A2',
          xpReward: 8
        },
        {
          id: 3,
          englishWord: 'understand',
          portugueseTranslation: 'entender',
          phonetic: '/ˌʌndərˈstænd/',
          definition: 'To comprehend or grasp the meaning',
          exampleSentence: 'I understand what you mean.',
          cefrLevel: 'A2',
          xpReward: 10
        }
      ])
    } finally {
      setLoading(false)
    }
  }

  const loadCompletedCards = async () => {
    try {
      const response = await axios.get('/api/vocabulary/progress', { withCredentials: true })
      setCompletedCards(response.data || [])
    } catch (error) {
      console.error('Error loading completed cards:', error)
      setCompletedCards([])
    }
  }

  const reviewCard = async (result) => {
    console.log('reviewCard called with result:', result)
    console.log('currentIndex:', currentIndex, 'cards.length:', cards.length)
    
    if (currentIndex >= cards.length) {
      console.log('No more cards to review')
      return
    }

    try {
      const currentCard = cards[currentIndex]
      console.log('Current card:', currentCard)
      
      // Try to send to API, but don't fail if it doesn't work
      try {
        await axios.post('/api/vocabulary/review', {
          wordId: currentCard.id,
          result: result,
          timeSpent: 30
        }, { withCredentials: true })
        console.log('API call successful')
      } catch (apiError) {
        console.log('API not available, continuing with local state update:', apiError.message)
      }

      // Update XP locally
      if (result === 'correct') {
        const xpGain = currentCard.xpReward || 5
        console.log('Adding XP:', xpGain)
        setXp(prev => prev + xpGain)
      }

      // Move to next card
      if (currentIndex < cards.length - 1) {
        console.log('Moving to next card')
        setCurrentIndex(prev => prev + 1)
        setShowAnswer(false)
      } else {
        console.log('All cards completed')
        // All cards completed
        setActiveTab('completed')
        loadCompletedCards()
      }
    } catch (error) {
      console.error('Error reviewing card:', error)
      // Even if there's an error, move to next card
      if (currentIndex < cards.length - 1) {
        setCurrentIndex(prev => prev + 1)
        setShowAnswer(false)
      } else {
        setActiveTab('completed')
        loadCompletedCards()
      }
    }
  }

  const startNewSession = () => {
    setCurrentIndex(0)
    setShowAnswer(false)
    setActiveTab('review')
    loadCards()
  }

  const getDifficultyColor = (level) => {
    const colors = {
      'A1': 'from-green-500 to-emerald-500',
      'A2': 'from-blue-500 to-cyan-500',
      'B1': 'from-yellow-500 to-orange-500',
      'B2': 'from-purple-500 to-pink-500',
      'C1': 'from-red-500 to-pink-500',
      'C2': 'from-gray-500 to-slate-500'
    }
    return colors[level] || colors['A1']
  }

  if (loading) {
    return (
      <div className="min-h-screen bg-gradient-to-br from-slate-50 to-blue-50">
        <Navbar />
        <div className="flex items-center justify-center min-h-[60vh]">
          <div className="text-center">
            <div className="animate-spin rounded-full h-12 w-12 border-4 border-blue-500 border-t-transparent mx-auto mb-4"></div>
            <p className="text-gray-600">Carregando vocabulário...</p>
          </div>
        </div>
      </div>
    )
  }

  return (
    <div className="min-h-screen bg-gradient-to-br from-slate-50 to-blue-50">
      <Navbar />
      
      <main className="container mx-auto px-4 py-8 max-w-6xl">
        {/* Header */}
        <div className="mb-8">
          <h1 className="text-4xl font-bold text-gray-900 mb-2">Vocabulário</h1>
          <p className="text-xl text-gray-600">Expanda seu vocabulário com repetição espaçada</p>
        </div>

        {/* Stats */}
        <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mb-8">
          <div className="bg-white rounded-2xl p-6 shadow-lg hover:shadow-xl transition-all duration-300">
            <div className="flex items-center gap-4">
              <div className="w-12 h-12 bg-gradient-to-r from-blue-500 to-cyan-500 rounded-2xl flex items-center justify-center">
                <Zap className="w-6 h-6 text-white" />
              </div>
              <div>
                <div className="text-2xl font-bold text-gray-900">{xp}</div>
                <div className="text-gray-600">XP Total</div>
              </div>
            </div>
          </div>
          
          <div className="bg-white rounded-2xl p-6 shadow-lg hover:shadow-xl transition-all duration-300">
            <div className="flex items-center gap-4">
              <div className="w-12 h-12 bg-gradient-to-r from-orange-500 to-red-500 rounded-2xl flex items-center justify-center">
                <Flame className="w-6 h-6 text-white" />
              </div>
              <div>
                <div className="text-2xl font-bold text-gray-900">{streak}</div>
                <div className="text-gray-600">Sequência</div>
              </div>
            </div>
          </div>
          
          <div className="bg-white rounded-2xl p-6 shadow-lg hover:shadow-xl transition-all duration-300">
            <div className="flex items-center gap-4">
              <div className="w-12 h-12 bg-gradient-to-r from-green-500 to-emerald-500 rounded-2xl flex items-center justify-center">
                <Target className="w-6 h-6 text-white" />
              </div>
              <div>
                <div className="text-2xl font-bold text-gray-900">{completedCards.length}</div>
                <div className="text-gray-600">Palavras Dominadas</div>
              </div>
            </div>
          </div>
        </div>

        {/* Tabs */}
        <div className="mb-8">
          <div className="flex space-x-2 bg-white p-2 rounded-2xl shadow-lg">
            <button
              onClick={() => setActiveTab('review')}
              className={`flex-1 flex items-center justify-center gap-2 py-3 px-6 rounded-xl font-semibold transition-all duration-200 ${
                activeTab === 'review'
                  ? 'bg-blue-500 text-white shadow-lg'
                  : 'text-gray-600 hover:bg-gray-100'
              }`}
            >
              <BookOpen size={20} />
              Revisar
            </button>
            <button
              onClick={() => setActiveTab('new')}
              className={`flex-1 flex items-center justify-center gap-2 py-3 px-6 rounded-xl font-semibold transition-all duration-200 ${
                activeTab === 'new'
                  ? 'bg-green-500 text-white shadow-lg'
                  : 'text-gray-600 hover:bg-gray-100'
              }`}
            >
              <Play size={20} />
              Nova Sessão
            </button>
            <button
              onClick={() => {
                setActiveTab('completed')
                loadCompletedCards()
              }}
              className={`flex-1 flex items-center justify-center gap-2 py-3 px-6 rounded-xl font-semibold transition-all duration-200 ${
                activeTab === 'completed'
                  ? 'bg-purple-500 text-white shadow-lg'
                  : 'text-gray-600 hover:bg-gray-100'
              }`}
            >
              <Award size={20} />
              Concluídas
            </button>
          </div>
        </div>

        {/* Content */}
        {activeTab === 'review' && (
          <div className="space-y-6">
            {cards.length === 0 ? (
              <div className="text-center py-16">
                <div className="w-24 h-24 bg-gradient-to-r from-blue-500 to-cyan-500 rounded-full flex items-center justify-center mx-auto mb-6">
                  <BookOpen size={40} className="text-white" />
                </div>
                <h2 className="text-2xl font-bold text-gray-900 mb-4">
                  Nenhuma palavra para revisar!
                </h2>
                <p className="text-lg text-gray-600 mb-8">Volte mais tarde para revisar novas palavras.</p>
                <button 
                  onClick={startNewSession}
                  className="bg-gradient-to-r from-green-500 to-emerald-500 text-white font-bold py-3 px-8 rounded-2xl hover:scale-105 transition-all duration-300 shadow-lg hover:shadow-xl flex items-center gap-2 mx-auto"
                >
                  <Play size={20} />
                  Começar Nova Sessão
                </button>
              </div>
            ) : currentIndex >= cards.length ? (
              <div className="text-center py-16">
                <div className="w-24 h-24 bg-gradient-to-r from-green-500 to-emerald-500 rounded-full flex items-center justify-center mx-auto mb-6">
                  <Check size={40} className="text-white" />
                </div>
                <h2 className="text-3xl font-bold text-gray-900 mb-4">
                  Parabéns! 🎉
                </h2>
                <p className="text-xl text-gray-600 mb-8">Você revisou todas as palavras!</p>
                <div className="flex gap-4 justify-center">
                  <button 
                    onClick={startNewSession}
                    className="bg-gradient-to-r from-blue-500 to-cyan-500 text-white font-bold py-3 px-8 rounded-2xl hover:scale-105 transition-all duration-300 shadow-lg hover:shadow-xl flex items-center gap-2"
                  >
                    <RefreshCw size={20} />
                    Revisar Novamente
                  </button>
                  <button 
                    onClick={() => setActiveTab('completed')}
                    className="bg-gradient-to-r from-purple-500 to-pink-500 text-white font-bold py-3 px-8 rounded-2xl hover:scale-105 transition-all duration-300 shadow-lg hover:shadow-xl flex items-center gap-2"
                  >
                    <Award size={20} />
                    Ver Progresso
                  </button>
                </div>
              </div>
            ) : (
              <div className="space-y-6">
                {/* Progress */}
                <div className="bg-white rounded-2xl p-6 shadow-lg">
                  <div className="flex items-center justify-between mb-4">
                    <span className="text-lg font-semibold text-gray-900">Progresso</span>
                    <span className="text-lg font-bold text-blue-600">{currentIndex + 1} / {cards.length}</span>
                  </div>
                  <div className="w-full bg-gray-200 rounded-full h-3">
                    <div 
                      className="bg-gradient-to-r from-blue-500 to-cyan-500 h-3 rounded-full transition-all duration-300"
                      style={{ width: `${((currentIndex + 1) / cards.length) * 100}%` }}
                    ></div>
                  </div>
                </div>

                {/* Card */}
                <div className="bg-white rounded-3xl p-8 shadow-2xl hover:shadow-3xl transition-all duration-300">
                  <div className="text-center">
                    <div className="flex items-center justify-center gap-2 mb-6">
                      <div className={`w-8 h-8 bg-gradient-to-r ${getDifficultyColor(cards[currentIndex]?.cefrLevel)} rounded-lg flex items-center justify-center`}>
                        <Star className="w-4 h-4 text-white" />
                      </div>
                      <span className="text-sm font-semibold text-gray-600 uppercase">
                        Nível {cards[currentIndex]?.cefrLevel}
                      </span>
                    </div>

                    <h2 className="text-4xl font-bold text-gray-900 mb-4">
                      {cards[currentIndex]?.englishWord}
                    </h2>
                    
                    <p className="text-lg text-gray-600 mb-6">
                      {cards[currentIndex]?.phonetic}
                    </p>

                    {!showAnswer ? (
                      <button
                        onClick={() => setShowAnswer(true)}
                        className="bg-gradient-to-r from-blue-500 to-cyan-500 text-white font-bold py-4 px-8 rounded-2xl hover:scale-105 transition-all duration-300 shadow-lg hover:shadow-xl flex items-center gap-2 mx-auto"
                      >
                        <Eye size={20} />
                        Mostrar Resposta
                      </button>
                    ) : (
                      <div className="space-y-6">
                        <div className="bg-gradient-to-r from-green-50 to-emerald-50 rounded-2xl p-6 border-2 border-green-200">
                          <h3 className="text-2xl font-bold text-green-800 mb-2">
                            {cards[currentIndex]?.portugueseTranslation}
                          </h3>
                          <p className="text-green-700 mb-4">
                            {cards[currentIndex]?.definition}
                          </p>
                          <p className="text-green-600 italic">
                            "{cards[currentIndex]?.exampleSentence}"
                          </p>
                        </div>

                        <div className="flex gap-4 justify-center">
                          <button
                            onClick={() => {
                              console.log('Não Lembro clicked')
                              reviewCard('incorrect')
                            }}
                            className="bg-gradient-to-r from-red-500 to-pink-500 text-white font-bold py-3 px-6 rounded-2xl hover:scale-105 transition-all duration-300 shadow-lg hover:shadow-xl flex items-center gap-2 cursor-pointer"
                          >
                            <X size={20} />
                            Não Lembro
                          </button>
                          <button
                            onClick={() => {
                              console.log('Lembro Bem clicked')
                              reviewCard('correct')
                            }}
                            className="bg-gradient-to-r from-green-500 to-emerald-500 text-white font-bold py-3 px-6 rounded-2xl hover:scale-105 transition-all duration-300 shadow-lg hover:shadow-xl flex items-center gap-2 cursor-pointer"
                          >
                            <Check size={20} />
                            Lembro Bem
                          </button>
                        </div>
                      </div>
                    )}
                  </div>
                </div>
              </div>
            )}
          </div>
        )}

        {activeTab === 'new' && (
          <div className="text-center py-16">
            <div className="w-24 h-24 bg-gradient-to-r from-green-500 to-emerald-500 rounded-full flex items-center justify-center mx-auto mb-6">
              <Play size={40} className="text-white" />
            </div>
            <h2 className="text-3xl font-bold text-gray-900 mb-4">
              Começar Nova Sessão
            </h2>
            <p className="text-xl text-gray-600 mb-8">
              Pratique novas palavras e expanda seu vocabulário!
            </p>
            <button 
              onClick={startNewSession}
              className="bg-gradient-to-r from-green-500 to-emerald-500 text-white font-bold py-4 px-8 rounded-2xl hover:scale-105 transition-all duration-300 shadow-lg hover:shadow-xl flex items-center gap-2 mx-auto"
            >
              <Play size={24} />
              Começar Agora
            </button>
          </div>
        )}

        {activeTab === 'completed' && (
          <div className="space-y-6">
            <h2 className="text-2xl font-bold text-gray-900 mb-6">
              Palavras Dominadas ({completedCards.length})
            </h2>
            
            {completedCards.length === 0 ? (
              <div className="text-center py-16">
                <div className="w-24 h-24 bg-gradient-to-r from-purple-500 to-pink-500 rounded-full flex items-center justify-center mx-auto mb-6">
                  <Award size={40} className="text-white" />
                </div>
                <h3 className="text-2xl font-bold text-gray-900 mb-4">
                  Nenhuma palavra dominada ainda
                </h3>
                <p className="text-lg text-gray-600 mb-8">
                  Complete algumas sessões para ver seu progresso aqui!
                </p>
                <button 
                  onClick={() => setActiveTab('new')}
                  className="bg-gradient-to-r from-blue-500 to-cyan-500 text-white font-bold py-3 px-8 rounded-2xl hover:scale-105 transition-all duration-300 shadow-lg hover:shadow-xl flex items-center gap-2 mx-auto"
                >
                  <Play size={20} />
                  Começar Primeira Sessão
                </button>
              </div>
            ) : (
              <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                {completedCards.map((card, index) => (
                  <div key={index} className="bg-white rounded-2xl p-6 shadow-lg hover:shadow-xl transition-all duration-300 hover:scale-105">
                    <div className="flex items-center gap-3 mb-4">
                      <div className={`w-8 h-8 bg-gradient-to-r ${getDifficultyColor(card.cefrLevel)} rounded-lg flex items-center justify-center`}>
                        <Star className="w-4 h-4 text-white" />
                      </div>
                      <span className="text-sm font-semibold text-gray-600 uppercase">
                        {card.cefrLevel}
                      </span>
                    </div>
                    <h3 className="text-xl font-bold text-gray-900 mb-2">
                      {card.englishWord}
                    </h3>
                    <p className="text-gray-600 mb-4">
                      {card.portugueseTranslation}
                    </p>
                    <div className="flex items-center gap-2 text-green-600">
                      <CheckCircle size={16} />
                      <span className="text-sm font-semibold">Dominada</span>
                    </div>
                  </div>
                ))}
              </div>
            )}
          </div>
        )}

        {/* Study Tip */}
        <div className="mt-12 bg-gradient-to-r from-blue-50 to-cyan-50 rounded-2xl p-6 border border-blue-200">
          <div className="flex items-center gap-3 mb-3">
            <Sparkles className="w-6 h-6 text-blue-600" />
            <h3 className="text-lg font-bold text-blue-900">Dica de Estudo</h3>
          </div>
          <p className="text-blue-800">
            Seja honesto com sua avaliação. Isso ajuda o algoritmo a programar a próxima revisão no momento ideal para fortalecer sua memória!
          </p>
        </div>
      </main>
    </div>
  )
}