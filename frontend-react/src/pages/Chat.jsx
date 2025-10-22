import { useState, useRef, useEffect } from 'react'
import { useAuth } from '../context/AuthContext'
import Navbar from '../components/Navbar'
import axios from 'axios'
import { Send, Bot, User as UserIcon, Loader, Sparkles, Zap, Star, Heart, Gem, RotateCcw } from 'lucide-react'

export default function Chat() {
  const { user } = useAuth()
  const [messages, setMessages] = useState([])
  const [inputText, setInputText] = useState('')
  const [loading, setLoading] = useState(false)
  const [xp, setXp] = useState(0)
  const [hearts, setHearts] = useState(5)
  const messagesEndRef = useRef(null)

  const scrollToBottom = () => {
    messagesEndRef.current?.scrollIntoView({ behavior: "smooth" })
  }

  useEffect(() => {
    scrollToBottom()
  }, [messages])

  const sendMessage = async (e) => {
    e.preventDefault()
    if (!inputText.trim() || loading) return

    const userMessage = {
      role: 'user',
      text: inputText,
      timestamp: new Date()
    }

    setMessages(prev => [...prev, userMessage])
    setInputText('')
    setLoading(true)

    try {
      const response = await axios.post('/api/tutor/chat', {
        userText: inputText,
        userLevel: user.cefrLevel,
        mode: 'conversation'
      }, { withCredentials: true })

      const tutorMessage = {
        role: 'tutor',
        text: response.data.reply,
        corrections: response.data.corrections,
        exercise: response.data.miniExercise,
        timestamp: new Date()
      }

      setMessages(prev => [...prev, tutorMessage])
      
      // Simulate XP gain
      setXp(prev => prev + 10)
    } catch (error) {
      console.error('Error sending message:', error)
      const errorMessage = {
        role: 'tutor',
        text: 'Desculpe, ocorreu um erro. Por favor, tente novamente.',
        timestamp: new Date()
      }
      setMessages(prev => [...prev, errorMessage])
    } finally {
      setLoading(false)
    }
  }

  const quickStarters = [
    "Tell me about your day",
    "What's your favorite food?",
    "Describe your family",
    "Talk about your hobbies"
  ]

  return (
    <div className="min-h-screen bg-gradient-to-br from-slate-50 to-blue-50 flex flex-col">
      <Navbar />
      
      <main className="flex-1 max-w-6xl mx-auto w-full px-4 py-8 flex flex-col">
        {/* Header */}
        <div className="mb-8">
          <div className="flex items-center justify-between mb-4">
            <div>
              <h1 className="text-4xl font-bold text-gray-900 mb-2">Tutor IA</h1>
              <p className="text-xl text-gray-600">Pratique inglês com correções em tempo real</p>
            </div>
            
            {/* XP and Hearts */}
            <div className="flex items-center gap-4">
              <div className="flex items-center gap-2 bg-white px-4 py-3 rounded-2xl shadow-lg">
                <Zap className="text-blue-500" size={20} />
                <span className="font-bold text-lg">{xp} XP</span>
              </div>
              <div className="flex items-center gap-2 bg-white px-4 py-3 rounded-2xl shadow-lg">
                <Heart className="heart" size={20} />
                <span className="font-bold text-lg">{hearts}</span>
              </div>
            </div>
          </div>

          {/* Quick Starters */}
          {messages.length === 0 && (
            <div className="mb-6">
              <h3 className="text-lg font-semibold text-gray-900 mb-3">Comece com uma dessas frases:</h3>
              <div className="flex flex-wrap gap-3">
                {quickStarters.map((starter, index) => (
                  <button
                    key={index}
                    onClick={() => setInputText(starter)}
                    className="bg-white border-2 border-green-200 text-green-700 px-4 py-2 rounded-2xl hover:border-green-300 hover:bg-green-50 transition-all duration-200 font-medium"
                  >
                    {starter}
                  </button>
                ))}
              </div>
            </div>
          )}
        </div>

        {/* Chat Container */}
        <div className="flex-1 bg-white rounded-3xl shadow-2xl overflow-hidden flex flex-col">
          {/* Messages */}
          <div className="flex-1 p-6 overflow-y-auto max-h-[600px]">
            {messages.length === 0 ? (
              <div className="flex flex-col items-center justify-center h-full text-center text-gray-500">
                <div className="w-20 h-20 bg-gradient-to-r from-green-500 to-emerald-600 rounded-full flex items-center justify-center mb-6">
                  <Bot size={40} className="text-white" />
                </div>
                <h3 className="text-2xl font-bold text-gray-900 mb-2">Olá! Sou seu tutor de inglês</h3>
                <p className="text-lg text-gray-600 mb-4">Vamos conversar em inglês! Eu vou te ajudar a melhorar.</p>
                <div className="flex items-center gap-2 text-green-600">
                  <Sparkles size={20} />
                  <span className="font-semibold">Correções em tempo real</span>
                </div>
              </div>
            ) : (
              <div className="space-y-6">
                {messages.map((msg, idx) => (
                  <div key={idx} className={`flex gap-4 ${msg.role === 'user' ? 'justify-end' : ''}`}>
                    {msg.role === 'tutor' && (
                      <div className="w-12 h-12 bg-gradient-to-r from-green-500 to-emerald-600 rounded-2xl flex items-center justify-center flex-shrink-0">
                        <Bot size={24} className="text-white" />
                      </div>
                    )}
                    
                    <div className={`max-w-[75%] ${msg.role === 'user' ? 'order-first' : ''}`}>
                      <div className={`rounded-3xl p-6 ${
                        msg.role === 'user' 
                          ? 'bg-gradient-to-r from-green-500 to-emerald-600 text-white' 
                          : 'bg-gray-100 text-gray-900'
                      }`}>
                        <p className="whitespace-pre-wrap text-lg leading-relaxed">{msg.text}</p>
                      </div>

                      {msg.corrections && msg.corrections.length > 0 && (
                        <div className="mt-4 space-y-3">
                          <div className="flex items-center gap-2 text-green-600 font-semibold">
                            <Sparkles size={20} />
                            <span>Correções</span>
                          </div>
                          {msg.corrections.map((corr, i) => (
                            <div key={i} className="bg-yellow-50 border-2 border-yellow-200 rounded-2xl p-4">
                              <div className="flex items-center gap-3 mb-2">
                                <span className="text-red-600 line-through text-lg font-medium">{corr.original}</span>
                                <span className="text-green-600 text-lg font-bold">→</span>
                                <span className="text-green-600 text-lg font-bold">{corr.corrected}</span>
                              </div>
                              <p className="text-gray-700 mb-2">{corr.explanation}</p>
                              {corr.rule && (
                                <p className="text-sm text-gray-500 bg-gray-100 px-3 py-1 rounded-lg inline-block">
                                  💡 {corr.rule}
                                </p>
                              )}
                            </div>
                          ))}
                        </div>
                      )}

                      {msg.exercise && (
                        <div className="mt-4 bg-blue-50 border-2 border-blue-200 rounded-2xl p-6">
                          <div className="flex items-center gap-2 mb-4">
                            <Star className="text-blue-600" size={20} />
                            <span className="font-bold text-blue-800">Mini Exercício</span>
                          </div>
                          <p className="font-semibold text-lg mb-4">{msg.exercise.question}</p>
                          <div className="space-y-2">
                            {msg.exercise.options.map((opt, i) => (
                              <div key={i} className={`p-3 rounded-xl text-lg ${
                                i === msg.exercise.correct 
                                  ? 'bg-green-100 text-green-800 border-2 border-green-300' 
                                  : 'bg-gray-100 text-gray-700'
                              }`}>
                                {i === msg.exercise.correct ? '✅ ' : '○ '}{opt}
                              </div>
                            ))}
                          </div>
                          <p className="text-sm text-gray-600 mt-4 bg-white p-3 rounded-xl">
                            💡 {msg.exercise.explanation}
                          </p>
                        </div>
                      )}
                    </div>

                    {msg.role === 'user' && (
                      <div className="w-12 h-12 bg-gradient-to-r from-blue-500 to-cyan-600 rounded-2xl flex items-center justify-center flex-shrink-0">
                        <UserIcon size={24} className="text-white" />
                      </div>
                    )}
                  </div>
                ))}

                {loading && (
                  <div className="flex gap-4">
                    <div className="w-12 h-12 bg-gradient-to-r from-green-500 to-emerald-600 rounded-2xl flex items-center justify-center">
                      <Bot size={24} className="text-white" />
                    </div>
                    <div className="bg-gray-100 rounded-3xl p-6">
                      <div className="flex items-center gap-3">
                        <Loader className="animate-spin text-gray-600" size={24} />
                        <span className="text-gray-600 font-medium">Tutor está pensando...</span>
                      </div>
                    </div>
                  </div>
                )}
                <div ref={messagesEndRef} />
              </div>
            )}
          </div>

          {/* Input */}
          <div className="p-6 border-t border-gray-200 bg-gray-50">
            <form onSubmit={sendMessage} className="flex gap-4">
              <div className="flex-1 relative">
                <input
                  type="text"
                  value={inputText}
                  onChange={(e) => setInputText(e.target.value)}
                  placeholder="Digite sua mensagem em inglês..."
                  className="w-full px-6 py-4 pr-12 border-2 border-gray-200 rounded-2xl focus:outline-none focus:ring-4 focus:ring-green-100 focus:border-green-500 transition-all duration-200 text-lg"
                  disabled={loading}
                />
                <button
                  type="button"
                  onClick={() => setInputText('')}
                  className="absolute right-4 top-1/2 transform -translate-y-1/2 text-gray-400 hover:text-gray-600 transition-colors"
                >
                  <RotateCcw size={20} />
                </button>
              </div>
              <button 
                type="submit" 
                className="btn-primary flex items-center gap-2 px-8 py-4 text-xl"
                disabled={loading || !inputText.trim()}
              >
                <Send size={24} />
                Enviar
              </button>
            </form>
            
            <div className="flex items-center justify-between mt-4 text-sm text-gray-500">
              <div className="flex items-center gap-4">
                <span>💡 Dica: Seja natural e não se preocupe com erros!</span>
              </div>
              <div className="flex items-center gap-2">
                <span>+10 XP por mensagem</span>
              </div>
            </div>
          </div>
        </div>
      </main>
    </div>
  )
}

