#!/usr/bin/env python3
"""
ElevenLabs API Tester
Script completo para testear todas las funcionalidades de ElevenLabs API
Autor: Tu proyecto
Fecha: 2025
"""

import requests
import json
import os
import time
from datetime import datetime
from typing import Dict, List, Optional, Any
import base64

class ElevenLabsTester:
    def __init__(self, api_key: str):
        """
        Inicializar el tester de ElevenLabs API
        
        Args:
            api_key (str): Tu API key de ElevenLabs Creator
        """
        self.api_key = api_key
        self.base_url = "https://api.elevenlabs.io/v1"
        self.headers = {
            "Accept": "application/json",
            "xi-api-key": api_key,
            "Content-Type": "application/json"
        }
        
        # Crear carpeta para guardar audios
        self.output_dir = "elevenlabs_output"
        if not os.path.exists(self.output_dir):
            os.makedirs(self.output_dir)
            print(f"📁 Carpeta creada: {self.output_dir}")

    def test_connection(self) -> bool:
        """
        Probar la conexión con la API
        
        Returns:
            bool: True si la conexión es exitosa
        """
        print("🔗 Probando conexión con ElevenLabs API...")
        
        try:
            response = requests.get(f"{self.base_url}/user", headers=self.headers)
            
            if response.status_code == 200:
                user_data = response.json()
                print("✅ Conexión exitosa!")
                print(f"👤 Usuario: {user_data.get('email', 'N/A')}")
                print(f"📦 Plan: {user_data.get('subscription', {}).get('tier', 'N/A')}")
                return True
            else:
                print(f"❌ Error de conexión: {response.status_code}")
                print(f"Respuesta: {response.text}")
                return False
                
        except Exception as e:
            print(f"❌ Error: {str(e)}")
            return False

    def get_account_info(self) -> Dict[str, Any]:
        """
        Obtener información detallada de la cuenta
        
        Returns:
            Dict: Información de la cuenta
        """
        print("\n📊 Obteniendo información de la cuenta...")
        
        try:
            # Información del usuario
            user_response = requests.get(f"{self.base_url}/user", headers=self.headers)
            
            # Información de suscripción
            subscription_response = requests.get(f"{self.base_url}/user/subscription", headers=self.headers)
            
            if user_response.status_code == 200 and subscription_response.status_code == 200:
                user_data = user_response.json()
                sub_data = subscription_response.json()
                
                info = {
                    "email": user_data.get("email"),
                    "plan": sub_data.get("tier"),
                    "character_count": sub_data.get("character_count", 0),
                    "character_limit": sub_data.get("character_limit", 0),
                    "can_extend_character_limit": sub_data.get("can_extend_character_limit", False),
                    "allowed_to_extend_character_limit": sub_data.get("allowed_to_extend_character_limit", False),
                    "next_character_count_reset_unix": sub_data.get("next_character_count_reset_unix", 0)
                }
                
                # Mostrar información
                print(f"📧 Email: {info['email']}")
                print(f"📦 Plan: {info['plan']}")
                print(f"📝 Caracteres usados: {info['character_count']:,}")
                print(f"📊 Límite de caracteres: {info['character_limit']:,}")
                
                remaining = info['character_limit'] - info['character_count']
                print(f"✨ Caracteres restantes: {remaining:,}")
                
                if info['next_character_count_reset_unix'] > 0:
                    reset_date = datetime.fromtimestamp(info['next_character_count_reset_unix'])
                    print(f"🔄 Reseteo: {reset_date.strftime('%d/%m/%Y %H:%M')}")
                
                return info
            else:
                print("❌ Error obteniendo información de la cuenta")
                return {}
                
        except Exception as e:
            print(f"❌ Error: {str(e)}")
            return {}

    def get_voices(self) -> List[Dict[str, Any]]:
        """
        Obtener todas las voces disponibles
        
        Returns:
            List: Lista de voces disponibles
        """
        print("\n🎤 Obteniendo voces disponibles...")
        
        try:
            response = requests.get(f"{self.base_url}/voices", headers=self.headers)
            
            if response.status_code == 200:
                data = response.json()
                voices = data.get("voices", [])
                
                print(f"🎵 Total de voces disponibles: {len(voices)}")
                
                # Categorizar voces
                categories = {}
                for voice in voices:
                    category = voice.get("category", "unknown")
                    if category not in categories:
                        categories[category] = []
                    categories[category].append(voice)
                
                # Mostrar resumen por categoría
                for category, voice_list in categories.items():
                    print(f"📂 {category.capitalize()}: {len(voice_list)} voces")
                
                # Mostrar algunas voces de ejemplo
                print("\n🎭 Voces de ejemplo:")
                for i, voice in enumerate(voices[:10]):  # Mostrar primeras 10
                    name = voice.get("name", "Sin nombre")
                    voice_id = voice.get("voice_id", "N/A")
                    category = voice.get("category", "N/A")
                    gender = voice.get("labels", {}).get("gender", "N/A")
                    age = voice.get("labels", {}).get("age", "N/A")
                    
                    print(f"  {i+1:2d}. {name} | ID: {voice_id[:8]}... | {category} | {gender} | {age}")
                
                # Mostrar TODAS las voces con su ID
                print("\n🗂️ TODAS LAS VOCES DISPONIBLES:")
                for i, voice in enumerate(voices):
                    name = voice.get("name", "Sin nombre")
                    voice_id = voice.get("voice_id", "N/A")
                    print(f"{i+1:2d}. {name} | ID: {voice_id}")

                return voices
            else:
                print(f"❌ Error obteniendo voces: {response.status_code}")
                return []
                
        except Exception as e:
            print(f"❌ Error: {str(e)}")
            return []

    def get_models(self) -> List[Dict[str, Any]]:
        """
        Obtener modelos disponibles
        
        Returns:
            List: Lista de modelos disponibles
        """
        print("\n🤖 Obteniendo modelos disponibles...")
        
        try:
            response = requests.get(f"{self.base_url}/models", headers=self.headers)
            
            if response.status_code == 200:
                data = response.json()
                
                print("🔧 Modelos disponibles:")
                for i, model in enumerate(data, 1):
                    model_id = model.get("model_id", "N/A")
                    name = model.get("name", "Sin nombre")
                    description = model.get("description", "Sin descripción")
                    
                    print(f"  {i}. {name}")
                    print(f"     ID: {model_id}")
                    print(f"     Descripción: {description[:100]}...")
                    print()
                
                return data
            else:
                print(f"❌ Error obteniendo modelos: {response.status_code}")
                return []
                
        except Exception as e:
            print(f"❌ Error: {str(e)}")
            return []

    def text_to_speech(self, text: str, voice_id: str, model_id: str = "eleven_turbo_v2", 
                      voice_settings: Optional[Dict] = None, save_file: bool = True) -> Optional[bytes]:
        """
        Convertir texto a voz
        
        Args:
            text (str): Texto a convertir
            voice_id (str): ID de la voz a usar
            model_id (str): ID del modelo a usar
            voice_settings (Dict): Configuraciones de voz
            save_file (bool): Si guardar el archivo de audio
            
        Returns:
            bytes: Audio en bytes si es exitoso
        """
        print(f"\n🗣️ Generando audio...")
        print(f"📝 Texto: '{text[:50]}{'...' if len(text) > 50 else ''}'")
        print(f"🎤 Voice ID: {voice_id}")
        print(f"🤖 Model: {model_id}")
        
        # Configuraciones por defecto
        if voice_settings is None:
            voice_settings = {
                "stability": 0.75,
                "similarity_boost": 0.8,
                "style": 0.0,
                "use_speaker_boost": True
            }
        
        payload = {
            "text": text,
            "model_id": model_id,
            "voice_settings": voice_settings
        }
        
        headers_audio = {
            "Accept": "audio/mpeg",
            "Content-Type": "application/json",
            "xi-api-key": self.api_key
        }
        
        try:
            print("⏳ Generando...")
            start_time = time.time()
            
            response = requests.post(
                f"{self.base_url}/text-to-speech/{voice_id}",
                json=payload,
                headers=headers_audio
            )
            
            end_time = time.time()
            generation_time = end_time - start_time
            
            if response.status_code == 200:
                audio_content = response.content
                print(f"✅ Audio generado exitosamente!")
                print(f"⏱️ Tiempo: {generation_time:.2f} segundos")
                print(f"💾 Tamaño: {len(audio_content):,} bytes")
                
                if save_file:
                    # Crear nombre de archivo único
                    timestamp = datetime.now().strftime("%Y%m%d_%H%M%S")
                    filename = f"audio_{voice_id[:8]}_{timestamp}.mp3"
                    filepath = os.path.join(self.output_dir, filename)
                    
                    with open(filepath, 'wb') as f:
                        f.write(audio_content)
                    
                    print(f"💾 Guardado como: {filepath}")
                
                return audio_content
            else:
                print(f"❌ Error generando audio: {response.status_code}")
                print(f"Respuesta: {response.text}")
                return None
                
        except Exception as e:
            print(f"❌ Error: {str(e)}")
            return None

    def run_comprehensive_test(self, test_text: str = None) -> None:
        """
        Ejecutar un test completo de todas las funcionalidades
        
        Args:
            test_text (str): Texto personalizado para probar
        """
        print("🚀 INICIANDO TEST COMPLETO DE ELEVENLABS API")
        print("=" * 60)
        
        # Texto por defecto
        if test_text is None:
            test_text = "Hola, este es un test de la API de ElevenLabs. Estoy probando la calidad de síntesis de voz con diferentes configuraciones."
        
        # 1. Test de conexión
        if not self.test_connection():
            print("❌ No se puede continuar sin conexión")
            return
        
        # 2. Información de cuenta
        account_info = self.get_account_info()
        if not account_info:
            print("⚠️ No se pudo obtener información de la cuenta")
        
        # Verificar si hay caracteres suficientes
        remaining_chars = account_info.get('character_limit', 0) - account_info.get('character_count', 0)
        if remaining_chars < len(test_text):
            print(f"⚠️ Advertencia: Solo quedan {remaining_chars} caracteres, el texto tiene {len(test_text)}")
            response = input("¿Continuar? (y/n): ")
            if response.lower() != 'y':
                return
        
        # 3. Obtener voces
        voices = self.get_voices()
        if not voices:
            print("❌ No se pudieron obtener las voces")
            return
        
        # 4. Obtener modelos
        models = self.get_models()
        
        # 5. Test de generación de audio con diferentes voces
        print("\n🎬 INICIANDO TESTS DE AUDIO")
        print("-" * 40)
        
        # Seleccionar algunas voces para probar
        test_voices = []
        
        # Buscar voces específicas populares
        popular_voices = ["Bruno Torres", 
                        "Mario - Warm Storyteller", 
                        "Alex Comunicando", 
                        "El Faraon 2 - Ancient Narrator", 
                        "Melissa - Young voice from Costa Rica", 
                        "Lizy - Mystery Stories", "Lizy - Children Stories", 
                        "Elena - Stories and Narrations", 
                        "Kallixis - Monster & Deep"]
        
        for voice in voices:
            if voice.get("name") in popular_voices and len(test_voices) < 5:
                test_voices.append(voice)
        
        # Si no encontramos suficientes voces populares, agregar otras
        if len(test_voices) < 3:
            for voice in voices[:5]:
                if voice not in test_voices:
                    test_voices.append(voice)
                if len(test_voices) >= 3:
                    break
        
        # Generar audio con cada voz seleccionada
        successful_generations = 0
        for i, voice in enumerate(test_voices):
            voice_name = voice.get("name", "Unknown")
            voice_id = voice.get("voice_id")
            
            print(f"\n🎤 Test {i+1}/{len(test_voices)}: {voice_name}")
            
            # Diferentes configuraciones para probar
            settings = {
                "stability": 0.7 + (i * 0.1),  # Variar estabilidad
                "similarity_boost": 0.8,
                "style": 0.0,
                "use_speaker_boost": True
            }
            
            audio = self.text_to_speech(
                text=test_text,
                voice_id=voice_id,
                voice_settings=settings
            )
            
            if audio:
                successful_generations += 1
            
            # Pausa para evitar rate limiting
            if i < len(test_voices) - 1:
                print("⏳ Esperando 2 segundos...")
                time.sleep(2)
        
        # 6. Resumen final
        print("\n📊 RESUMEN DEL TEST")
        print("=" * 40)
        print(f"✅ Generaciones exitosas: {successful_generations}/{len(test_voices)}")
        print(f"📁 Archivos guardados en: {self.output_dir}")
        
        if successful_generations > 0:
            print("🎉 ¡Test completado exitosamente!")
            print("💡 Revisa los archivos de audio generados para evaluar la calidad")
        else:
            print("❌ No se pudo generar ningún audio")
        
        # Información adicional
        print(f"\n📝 Caracteres utilizados en este test: ~{len(test_text) * successful_generations}")
        print(f"💰 Costo aproximado: Incluido en tu plan Creator")

def main():
    """
    Función principal para ejecutar el tester
    """
    print("🎵 ELEVENLABS API TESTER")
    print("=" * 50)
    
    # API key
    api_key = "sk_1a23cde668fc657bce32db522b74fe25b6bbe1b8b7c3407b"
    
    # Crear instancia del tester
    tester = ElevenLabsTester(api_key)
    
    # Menú de opciones
    while True:
        print("\n📋 OPCIONES DISPONIBLES:")
        print("1. Test rápido de conexión")
        print("2. Ver información de cuenta")
        print("3. Listar voces disponibles")
        print("4. Listar modelos disponibles")
        print("5. Generar audio personalizado")
        print("6. Test completo automático")
        print("0. Salir")
        
        choice = input("\n🎯 Selecciona una opción (0-6): ").strip()
        
        if choice == "0":
            print("👋 ¡Hasta luego!")
            break
        elif choice == "1":
            tester.test_connection()
        elif choice == "2":
            tester.get_account_info()
        elif choice == "3":
            tester.get_voices()
        elif choice == "4":
            tester.get_models()
        elif choice == "5":
            text = input("📝 Ingresa el texto a convertir: ").strip()
            if text:
                voices = tester.get_voices()
                if voices:
                    print("\n🎤 Selecciona una voz:")
                    for i, voice in enumerate(voices[:10]):
                        print(f"{i+1}. {voice.get('name')} ({voice.get('voice_id')[:8]}...)")
                    
                    try:
                        voice_idx = int(input("Número de voz: ")) - 1
                        if 0 <= voice_idx < min(10, len(voices)):
                            selected_voice = voices[voice_idx]
                            tester.text_to_speech(text, selected_voice.get('voice_id'))
                        else:
                            print("❌ Selección inválida")
                    except ValueError:
                        print("❌ Número inválido")
            else:
                print("❌ Texto requerido")
        elif choice == "6":
            custom_text = input("📝 Texto personalizado (Enter para usar por defecto): ").strip()
            tester.run_comprehensive_test(custom_text if custom_text else None)
        else:
            print("❌ Opción inválida")

if __name__ == "__main__":
    main()