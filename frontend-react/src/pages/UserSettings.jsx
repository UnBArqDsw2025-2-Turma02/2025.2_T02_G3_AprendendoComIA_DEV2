import { useState, useEffect } from 'react'
import { useAuth } from '../context/AuthContext'
import { useNavigate } from 'react-router-dom'
import { 
  User, 
  Bell, 
  Shield, 
  Palette, 
  Globe, 
  Smartphone,
  Mail,
  Lock,
  Eye,
  EyeOff,
  Save,
  Camera,
  Check,
  X,
  Settings,
  Moon,
  Sun,
  Volume2,
  VolumeX,
  Wifi,
  WifiOff,
  MapPin,
  Calendar,
  Award,
  Target,
  BookOpen,
  MessageSquare,
  Trophy,
  Star,
  ArrowLeft
} from 'lucide-react'
import axios from 'axios'

export default function UserSettings() {
  const { user, updateUser } = useAuth()
  const navigate = useNavigate()
  const [activeTab, setActiveTab] = useState('profile')
  const [showPassword, setShowPassword] = useState(false)
  const [loading, setLoading] = useState(false)
  const [notifications, setNotifications] = useState({
    email: true,
    push: true,
    sms: false,
    weekly: true,
    achievements: true,
    reminders: true
  })

  const saveSettings = async () => {
    setLoading(true)
    try {
      // Assuming we have user ID from auth context
      const userId = user?.id || 1 // Fallback for demo
      
      // Prepare settings data (without profile photo to avoid 413 error)
      const settingsData = {
        dailyGoalMinutes: preferences.dailyGoal || 15,
        cefrLevel: preferences.level || 'A2',
        appearance: {
          theme: preferences.theme,
          language: preferences.language,
          timezone: preferences.timezone
        },
        learning: {
          sound: preferences.sound,
          vibration: preferences.vibration,
          autoPlay: preferences.autoPlay
        },
        notifications: {
          email: notifications.email,
          push: notifications.push,
          sms: notifications.sms,
          weekly: notifications.weekly,
          achievements: notifications.achievements,
          reminders: notifications.reminders
        },
        privacy: {
          analytics: false,
          shareProgress: true,
          publicProfile: true
        }
      }
      
      // Save settings first
      await axios.put(`/api/user-settings/preferences/${userId}`, settingsData, { withCredentials: true })
      
      // Save profile data separately (without photo for now)
      const profileDataToSave = {
        name: profileData.name,
        email: profileData.email,
        phone: profileData.phone,
        birthDate: profileData.birthDate,
        country: profileData.country,
        city: profileData.city
      }
      
      // Try to save profile data (if API supports it)
      try {
        await axios.put(`/api/user/profile/${userId}`, profileDataToSave, { withCredentials: true })
      } catch (profileError) {
        console.log('Profile API not available, saving locally only')
      }
      
      // Update user context with new data
      updateUser({
        name: profileData.name,
        email: profileData.email,
        phone: profileData.phone,
        birthDate: profileData.birthDate,
        country: profileData.country,
        city: profileData.city,
        profilePhoto: profileData.profilePhoto
      })
      
      // Show success message
      alert('Configurações salvas com sucesso!')
      
      // Redirect to dashboard after successful save
      setTimeout(() => {
        navigate('/dashboard')
      }, 1000)
      
    } catch (error) {
      console.error('Error saving settings:', error)
      alert('Erro ao salvar configurações. Tente novamente.')
    } finally {
      setLoading(false)
    }
  }
  const [preferences, setPreferences] = useState({
    theme: 'light',
    language: 'pt-BR',
    timezone: 'America/Sao_Paulo',
    sound: true,
    vibration: true,
    autoPlay: false
  })

  const [profileData, setProfileData] = useState({
    name: '',
    email: '',
    phone: '',
    birthDate: '',
    country: 'Brasil',
    city: '',
    profilePhoto: ''
  })

  // Load user data on component mount
  useEffect(() => {
    if (user) {
      setProfileData({
        name: user.name || '',
        email: user.email || '',
        phone: user.phone || '',
        birthDate: user.birthDate || '',
        country: user.country || 'Brasil',
        city: user.city || '',
        profilePhoto: user.profilePhoto || ''
      })
    }
  }, [user])

  const compressImage = (file, maxWidth = 300, maxHeight = 300, quality = 0.8) => {
    return new Promise((resolve) => {
      const canvas = document.createElement('canvas')
      const ctx = canvas.getContext('2d')
      const img = new Image()
      
      img.onload = () => {
        // Calculate new dimensions
        let { width, height } = img
        if (width > height) {
          if (width > maxWidth) {
            height = (height * maxWidth) / width
            width = maxWidth
          }
        } else {
          if (height > maxHeight) {
            width = (width * maxHeight) / height
            height = maxHeight
          }
        }
        
        canvas.width = width
        canvas.height = height
        
        // Draw and compress
        ctx.drawImage(img, 0, 0, width, height)
        const compressedDataUrl = canvas.toDataURL('image/jpeg', quality)
        resolve(compressedDataUrl)
      }
      
      img.src = URL.createObjectURL(file)
    })
  }

  const handlePhotoUpload = async (event) => {
    const file = event.target.files[0]
    if (file) {
      // Validate file type
      if (!file.type.startsWith('image/')) {
        alert('Por favor, selecione apenas arquivos de imagem')
        return
      }
      
      // Validate file size (max 2MB before compression)
      if (file.size > 2 * 1024 * 1024) {
        alert('A imagem deve ter no máximo 2MB')
        return
      }
      
      try {
        // Compress image to reduce size
        const compressedImage = await compressImage(file, 200, 200, 0.7)
        setProfileData({...profileData, profilePhoto: compressedImage})
      } catch (error) {
        console.error('Error compressing image:', error)
        alert('Erro ao processar a imagem')
      }
    }
  }

  const tabs = [
    { id: 'profile', label: 'Perfil', icon: User },
    { id: 'notifications', label: 'Notificações', icon: Bell },
    { id: 'privacy', label: 'Privacidade', icon: Shield },
    { id: 'appearance', label: 'Aparência', icon: Palette },
    { id: 'preferences', label: 'Preferências', icon: Settings }
  ]

  const handleNotificationChange = (key) => {
    setNotifications(prev => ({
      ...prev,
      [key]: !prev[key]
    }))
  }

  const handlePreferenceChange = (key, value) => {
    setPreferences(prev => ({
      ...prev,
      [key]: value
    }))
    
    // Apply theme changes immediately
    if (key === 'theme') {
      applyTheme(value)
    }
  }
  
  const applyTheme = (theme) => {
    const root = document.documentElement
    
    // Add transition class for smooth theme switching
    root.classList.add('theme-transition')
    
    setTimeout(() => {
      if (theme === 'dark') {
        root.classList.add('dark')
      } else if (theme === 'light') {
        root.classList.remove('dark')
      } else if (theme === 'auto') {
        // Auto theme based on system preference
        if (window.matchMedia('(prefers-color-scheme: dark)').matches) {
          root.classList.add('dark')
        } else {
          root.classList.remove('dark')
        }
      }
      
      // Remove transition class after theme is applied
      setTimeout(() => {
        root.classList.remove('theme-transition')
      }, 300)
    }, 50)
  }
  
  // Apply theme on component mount
  useEffect(() => {
    applyTheme(preferences.theme)
  }, [preferences.theme])

  return (
    <div className="min-h-screen bg-gradient-to-br from-slate-50 to-blue-50">
      {/* Header */}
      <div className="bg-white shadow-sm border-b border-gray-200">
        <div className="container mx-auto px-4 py-6">
          <div className="flex items-center justify-between">
            <div className="flex items-center gap-4">
              <div className="w-12 h-12 bg-gradient-to-r from-blue-500 to-blue-600 rounded-2xl flex items-center justify-center">
                <User className="text-white" size={24} />
              </div>
              <div>
                <h1 className="text-2xl font-bold text-gray-900">Configurações</h1>
                <p className="text-gray-600">Personalize sua experiência de aprendizado</p>
              </div>
            </div>
            <button className="btn-primary" onClick={saveSettings} disabled={loading}>
              <Save size={20} />
              {loading ? 'Salvando...' : 'Salvar Alterações'}
            </button>
          </div>
        </div>
      </div>

      {/* Back Button */}
      <div className="bg-white border-b border-gray-200">
        <div className="container mx-auto px-4 py-3">
          <button
            onClick={() => navigate('/dashboard')}
            className="flex items-center gap-2 px-3 py-1.5 text-sm text-gray-600 hover:bg-gray-100 rounded-lg transition-colors"
          >
            <ArrowLeft size={16} />
            <span>Voltar ao Dashboard</span>
          </button>
        </div>
      </div>

      <div className="container mx-auto px-4 py-8">
        <div className="flex flex-col lg:flex-row gap-8">
          {/* Sidebar */}
          <div className="lg:w-64">
            <nav className="bg-white rounded-2xl shadow-sm p-4">
              <div className="space-y-2">
                {tabs.map(tab => (
                  <button
                    key={tab.id}
                    onClick={() => setActiveTab(tab.id)}
                    className={`w-full flex items-center gap-3 px-4 py-3 rounded-xl font-medium transition-all ${
                      activeTab === tab.id
                        ? 'bg-blue-50 text-blue-600 border border-blue-200'
                        : 'text-gray-600 hover:bg-gray-50 hover:text-gray-900'
                    }`}
                  >
                    <tab.icon size={20} />
                    {tab.label}
                  </button>
                ))}
              </div>
            </nav>
          </div>

          {/* Main Content */}
          <div className="flex-1">
            {activeTab === 'profile' && (
              <div className="space-y-8">
                <div className="card">
                  <h2 className="text-xl font-bold text-gray-900 mb-6">Informações Pessoais</h2>
                  
                  <div className="flex items-center gap-6 mb-8">
                    <div className="relative">
                      <div className="w-24 h-24 bg-gradient-to-r from-blue-500 to-blue-600 rounded-full flex items-center justify-center text-white text-2xl font-bold overflow-hidden">
                        {profileData.profilePhoto ? (
                          <img 
                            src={profileData.profilePhoto} 
                            alt="Foto de perfil" 
                            className="w-full h-full object-cover rounded-full"
                          />
                        ) : (
                          profileData.name ? 
                            profileData.name.split(' ').map(n => n[0]).join('').toUpperCase() : 
                            user?.name ? 
                            user.name.split(' ').map(n => n[0]).join('').toUpperCase() : 
                            'U'
                        )}
                      </div>
                      <label className="absolute -bottom-2 -right-2 w-8 h-8 bg-white border-2 border-gray-200 rounded-full flex items-center justify-center hover:bg-gray-50 transition-colors cursor-pointer">
                        <Camera size={16} className="text-gray-600" />
                        <input
                          type="file"
                          accept="image/*"
                          onChange={handlePhotoUpload}
                          className="hidden"
                        />
                      </label>
                    </div>
                    <div>
                      <h3 className="text-lg font-semibold text-gray-900">{profileData.name || user?.name || 'Usuário'}</h3>
                      <p className="text-gray-600">{profileData.email || user?.email || 'email@exemplo.com'}</p>
                      <button className="text-blue-600 hover:text-blue-800 text-sm font-medium mt-1">
                        Alterar foto
                      </button>
                    </div>
                  </div>

                  <div className="grid md:grid-cols-2 gap-6">
                    <div>
                      <label className="block text-sm font-medium text-gray-700 mb-2">
                        Nome Completo
                      </label>
                      <input
                        type="text"
                        className="input-field"
                        value={profileData.name}
                        onChange={(e) => setProfileData({...profileData, name: e.target.value})}
                        placeholder="Seu nome completo"
                      />
                    </div>
                    <div>
                      <label className="block text-sm font-medium text-gray-700 mb-2">
                        Email
                      </label>
                      <input
                        type="email"
                        className="input-field"
                        value={profileData.email}
                        onChange={(e) => setProfileData({...profileData, email: e.target.value})}
                        placeholder="seu@email.com"
                      />
                    </div>
                    <div>
                      <label className="block text-sm font-medium text-gray-700 mb-2">
                        Telefone
                      </label>
                      <input
                        type="tel"
                        className="input-field"
                        value={profileData.phone}
                        onChange={(e) => setProfileData({...profileData, phone: e.target.value})}
                        placeholder="(11) 99999-9999"
                      />
                    </div>
                    <div>
                      <label className="block text-sm font-medium text-gray-700 mb-2">
                        Data de Nascimento
                      </label>
                      <input
                        type="date"
                        className="input-field"
                        value={profileData.birthDate}
                        onChange={(e) => setProfileData({...profileData, birthDate: e.target.value})}
                      />
                    </div>
                    <div>
                      <label className="block text-sm font-medium text-gray-700 mb-2">
                        País
                      </label>
                      <select 
                        className="input-field"
                        value={profileData.country}
                        onChange={(e) => setProfileData({...profileData, country: e.target.value})}
                      >
                        <option value="Brasil">Brasil</option>
                        <option value="Estados Unidos">Estados Unidos</option>
                        <option value="Canadá">Canadá</option>
                        <option value="Reino Unido">Reino Unido</option>
                      </select>
                    </div>
                    <div>
                      <label className="block text-sm font-medium text-gray-700 mb-2">
                        Cidade
                      </label>
                      <input
                        type="text"
                        className="input-field"
                        value={profileData.city}
                        onChange={(e) => setProfileData({...profileData, city: e.target.value})}
                        placeholder="Sua cidade"
                      />
                    </div>
                  </div>
                </div>

                <div className="card">
                  <h2 className="text-xl font-bold text-gray-900 mb-6">Segurança</h2>
                  
                  <div className="space-y-6">
                    <div>
                      <label className="block text-sm font-medium text-gray-700 mb-2">
                        Senha Atual
                      </label>
                      <div className="relative">
                        <input
                          type={showPassword ? 'text' : 'password'}
                          className="input-field pr-12"
                          placeholder="Digite sua senha atual"
                        />
                        <button
                          type="button"
                          className="absolute right-4 top-1/2 transform -translate-y-1/2 text-gray-500 hover:text-gray-700"
                          onClick={() => setShowPassword(!showPassword)}
                        >
                          {showPassword ? <EyeOff size={20} /> : <Eye size={20} />}
                        </button>
                      </div>
                    </div>
                    
                    <div>
                      <label className="block text-sm font-medium text-gray-700 mb-2">
                        Nova Senha
                      </label>
                      <input
                        type="password"
                        className="input-field"
                        placeholder="Digite sua nova senha"
                      />
                    </div>
                    
                    <div>
                      <label className="block text-sm font-medium text-gray-700 mb-2">
                        Confirmar Nova Senha
                      </label>
                      <input
                        type="password"
                        className="input-field"
                        placeholder="Confirme sua nova senha"
                      />
                    </div>
                  </div>
                </div>
              </div>
            )}

            {activeTab === 'notifications' && (
              <div className="space-y-8">
                <div className="card">
                  <h2 className="text-xl font-bold text-gray-900 mb-6">Preferências de Notificação</h2>
                  
                  <div className="space-y-6">
                    <div className="flex items-center justify-between">
                      <div>
                        <p className="font-medium text-gray-900">Notificações por Email</p>
                        <p className="text-sm text-gray-500">Receba atualizações importantes por email</p>
                      </div>
                      <button
                        onClick={() => handleNotificationChange('email')}
                        className={`relative inline-flex h-6 w-11 items-center rounded-full transition-colors ${
                          notifications.email ? 'bg-blue-600' : 'bg-gray-300'
                        }`}
                      >
                        <span className={`inline-block h-4 w-4 transform rounded-full bg-white transition-transform ${
                          notifications.email ? 'translate-x-6' : 'translate-x-1'
                        }`} />
                      </button>
                    </div>

                    <div className="flex items-center justify-between">
                      <div>
                        <p className="font-medium text-gray-900">Notificações Push</p>
                        <p className="text-sm text-gray-500">Receba notificações no seu dispositivo</p>
                      </div>
                      <button
                        onClick={() => handleNotificationChange('push')}
                        className={`relative inline-flex h-6 w-11 items-center rounded-full transition-colors ${
                          notifications.push ? 'bg-blue-600' : 'bg-gray-300'
                        }`}
                      >
                        <span className={`inline-block h-4 w-4 transform rounded-full bg-white transition-transform ${
                          notifications.push ? 'translate-x-6' : 'translate-x-1'
                        }`} />
                      </button>
                    </div>

                    <div className="flex items-center justify-between">
                      <div>
                        <p className="font-medium text-gray-900">Lembretes Semanais</p>
                        <p className="text-sm text-gray-500">Resumo semanal do seu progresso</p>
                      </div>
                      <button
                        onClick={() => handleNotificationChange('weekly')}
                        className={`relative inline-flex h-6 w-11 items-center rounded-full transition-colors ${
                          notifications.weekly ? 'bg-blue-600' : 'bg-gray-300'
                        }`}
                      >
                        <span className={`inline-block h-4 w-4 transform rounded-full bg-white transition-transform ${
                          notifications.weekly ? 'translate-x-6' : 'translate-x-1'
                        }`} />
                      </button>
                    </div>

                    <div className="flex items-center justify-between">
                      <div>
                        <p className="font-medium text-gray-900">Conquistas</p>
                        <p className="text-sm text-gray-500">Notifique quando conquistar novos objetivos</p>
                      </div>
                      <button
                        onClick={() => handleNotificationChange('achievements')}
                        className={`relative inline-flex h-6 w-11 items-center rounded-full transition-colors ${
                          notifications.achievements ? 'bg-blue-600' : 'bg-gray-300'
                        }`}
                      >
                        <span className={`inline-block h-4 w-4 transform rounded-full bg-white transition-transform ${
                          notifications.achievements ? 'translate-x-6' : 'translate-x-1'
                        }`} />
                      </button>
                    </div>
                  </div>
                </div>

                <div className="card">
                  <h2 className="text-xl font-bold text-gray-900 mb-6">Horários de Notificação</h2>
                  
                  <div className="grid md:grid-cols-2 gap-6">
                    <div>
                      <label className="block text-sm font-medium text-gray-700 mb-2">
                        Horário de Início
                      </label>
                      <input
                        type="time"
                        className="input-field"
                        defaultValue="08:00"
                      />
                    </div>
                    <div>
                      <label className="block text-sm font-medium text-gray-700 mb-2">
                        Horário de Fim
                      </label>
                      <input
                        type="time"
                        className="input-field"
                        defaultValue="22:00"
                      />
                    </div>
                  </div>
                </div>
              </div>
            )}

            {activeTab === 'privacy' && (
              <div className="space-y-8">
                <div className="card">
                  <h2 className="text-xl font-bold text-gray-900 mb-6">Configurações de Privacidade</h2>
                  
                  <div className="space-y-6">
                    <div className="flex items-center justify-between">
                      <div>
                        <p className="font-medium text-gray-900">Perfil Público</p>
                        <p className="text-sm text-gray-500">Permitir que outros usuários vejam seu perfil</p>
                      </div>
                      <button className="relative inline-flex h-6 w-11 items-center rounded-full bg-blue-600 transition-colors">
                        <span className="inline-block h-4 w-4 transform rounded-full bg-white transition-transform translate-x-6" />
                      </button>
                    </div>

                    <div className="flex items-center justify-between">
                      <div>
                        <p className="font-medium text-gray-900">Compartilhar Progresso</p>
                        <p className="text-sm text-gray-500">Permitir que amigos vejam seu progresso</p>
                      </div>
                      <button className="relative inline-flex h-6 w-11 items-center rounded-full bg-blue-600 transition-colors">
                        <span className="inline-block h-4 w-4 transform rounded-full bg-white transition-transform translate-x-6" />
                      </button>
                    </div>

                    <div className="flex items-center justify-between">
                      <div>
                        <p className="font-medium text-gray-900">Analytics de Uso</p>
                        <p className="text-sm text-gray-500">Permitir coleta de dados para melhorar a experiência</p>
                      </div>
                      <button className="relative inline-flex h-6 w-11 items-center rounded-full bg-gray-300 transition-colors">
                        <span className="inline-block h-4 w-4 transform rounded-full bg-white transition-transform translate-x-1" />
                      </button>
                    </div>
                  </div>
                </div>

                <div className="card">
                  <h2 className="text-xl font-bold text-gray-900 mb-6">Dados Pessoais</h2>
                  
                  <div className="space-y-4">
                    <div className="flex items-center justify-between p-4 bg-gray-50 rounded-2xl">
                      <div>
                        <p className="font-medium text-gray-900">Exportar Dados</p>
                        <p className="text-sm text-gray-500">Baixar uma cópia dos seus dados</p>
                      </div>
                      <button className="btn-outline">
                        <Globe size={16} />
                        Exportar
                      </button>
                    </div>

                    <div className="flex items-center justify-between p-4 bg-gray-50 rounded-2xl">
                      <div>
                        <p className="font-medium text-gray-900">Excluir Conta</p>
                        <p className="text-sm text-gray-500">Remover permanentemente sua conta e dados</p>
                      </div>
                      <button className="text-red-600 hover:text-red-800 font-medium">
                        Excluir Conta
                      </button>
                    </div>
                  </div>
                </div>
              </div>
            )}

            {activeTab === 'appearance' && (
              <div className="space-y-8">
                <div className="card">
                  <h2 className="text-xl font-bold text-gray-900 mb-6">Tema e Aparência</h2>
                  
                  <div className="space-y-6">
                    <div>
                      <label className="block text-sm font-medium text-gray-700 mb-4">
                        Tema
                      </label>
                      <div className="grid grid-cols-3 gap-4">
                        {[
                          { id: 'light', label: 'Claro', icon: Sun },
                          { id: 'dark', label: 'Escuro', icon: Moon },
                          { id: 'auto', label: 'Automático', icon: Settings }
                        ].map(theme => (
                          <button
                            key={theme.id}
                            onClick={() => handlePreferenceChange('theme', theme.id)}
                            className={`p-4 rounded-2xl border-2 transition-all ${
                              preferences.theme === theme.id
                                ? 'border-blue-500 bg-blue-50'
                                : 'border-gray-200 hover:border-gray-300'
                            }`}
                          >
                            <theme.icon size={24} className="mx-auto mb-2" />
                            <p className="text-sm font-medium">{theme.label}</p>
                          </button>
                        ))}
                      </div>
                    </div>

                    <div>
                      <label className="block text-sm font-medium text-gray-700 mb-2">
                        Idioma da Interface
                      </label>
                      <select 
                        className="input-field"
                        value={preferences.language}
                        onChange={(e) => handlePreferenceChange('language', e.target.value)}
                      >
                        <option value="pt-BR">Português (Brasil)</option>
                        <option value="en-US">English (US)</option>
                        <option value="es-ES">Español</option>
                        <option value="fr-FR">Français</option>
                      </select>
                    </div>

                    <div>
                      <label className="block text-sm font-medium text-gray-700 mb-2">
                        Fuso Horário
                      </label>
                      <select 
                        className="input-field"
                        value={preferences.timezone}
                        onChange={(e) => handlePreferenceChange('timezone', e.target.value)}
                      >
                        <option value="America/Sao_Paulo">São Paulo (UTC-3)</option>
                        <option value="America/New_York">New York (UTC-5)</option>
                        <option value="Europe/London">London (UTC+0)</option>
                        <option value="Asia/Tokyo">Tokyo (UTC+9)</option>
                      </select>
                    </div>
                  </div>
                </div>
              </div>
            )}

            {activeTab === 'preferences' && (
              <div className="space-y-8">
                <div className="card">
                  <h2 className="text-xl font-bold text-gray-900 mb-6">Preferências de Aprendizado</h2>
                  
                  <div className="space-y-6">
                    <div>
                      <label className="block text-sm font-medium text-gray-700 mb-2">
                        Nível de Inglês
                      </label>
                      <div className="grid grid-cols-5 gap-2">
                        {['A1', 'A2', 'B1', 'B2', 'C1'].map(level => (
                          <button
                            key={level}
                            className={`p-3 rounded-xl border-2 font-bold transition-all ${
                              level === 'B2'
                                ? 'border-blue-500 bg-blue-50 text-blue-600'
                                : 'border-gray-200 hover:border-gray-300'
                            }`}
                          >
                            {level}
                          </button>
                        ))}
                      </div>
                    </div>

                    <div>
                      <label className="block text-sm font-medium text-gray-700 mb-2">
                        Objetivo Diário (minutos)
                      </label>
                      <input
                        type="range"
                        min="5"
                        max="120"
                        step="5"
                        defaultValue="15"
                        className="w-full h-2 bg-gray-200 rounded-lg appearance-none cursor-pointer"
                      />
                      <div className="flex justify-between text-sm text-gray-500 mt-1">
                        <span>5 min</span>
                        <span className="font-medium text-blue-600">15 min</span>
                        <span>120 min</span>
                      </div>
                    </div>

                    <div className="flex items-center justify-between">
                      <div>
                        <p className="font-medium text-gray-900">Som de Notificações</p>
                        <p className="text-sm text-gray-500">Reproduzir sons ao completar exercícios</p>
                      </div>
                      <button
                        onClick={() => handlePreferenceChange('sound', !preferences.sound)}
                        className={`relative inline-flex h-6 w-11 items-center rounded-full transition-colors ${
                          preferences.sound ? 'bg-blue-600' : 'bg-gray-300'
                        }`}
                      >
                        <span className={`inline-block h-4 w-4 transform rounded-full bg-white transition-transform ${
                          preferences.sound ? 'translate-x-6' : 'translate-x-1'
                        }`} />
                      </button>
                    </div>

                    <div className="flex items-center justify-between">
                      <div>
                        <p className="font-medium text-gray-900">Vibração</p>
                        <p className="text-sm text-gray-500">Vibrar ao receber notificações</p>
                      </div>
                      <button
                        onClick={() => handlePreferenceChange('vibration', !preferences.vibration)}
                        className={`relative inline-flex h-6 w-11 items-center rounded-full transition-colors ${
                          preferences.vibration ? 'bg-blue-600' : 'bg-gray-300'
                        }`}
                      >
                        <span className={`inline-block h-4 w-4 transform rounded-full bg-white transition-transform ${
                          preferences.vibration ? 'translate-x-6' : 'translate-x-1'
                        }`} />
                      </button>
                    </div>
                  </div>
                </div>

                <div className="card">
                  <h2 className="text-xl font-bold text-gray-900 mb-6">Resumo do Progresso</h2>
                  
                  <div className="grid md:grid-cols-3 gap-6">
                    <div className="text-center p-6 bg-gradient-to-br from-blue-50 to-blue-100 rounded-2xl">
                      <BookOpen className="w-8 h-8 text-blue-600 mx-auto mb-3" />
                      <p className="text-2xl font-bold text-gray-900">156</p>
                      <p className="text-sm text-gray-600">Lições Completas</p>
                    </div>
                    
                    <div className="text-center p-6 bg-gradient-to-br from-green-50 to-green-100 rounded-2xl">
                      <Trophy className="w-8 h-8 text-green-600 mx-auto mb-3" />
                      <p className="text-2xl font-bold text-gray-900">23</p>
                      <p className="text-sm text-gray-600">Conquistas</p>
                    </div>
                    
                    <div className="text-center p-6 bg-gradient-to-br from-purple-50 to-purple-100 rounded-2xl">
                      <Star className="w-8 h-8 text-purple-600 mx-auto mb-3" />
                      <p className="text-2xl font-bold text-gray-900">2,340</p>
                      <p className="text-sm text-gray-600">Pontos XP</p>
                    </div>
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
