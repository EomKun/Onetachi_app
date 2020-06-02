package com.android.onetachi.retrofit

import com.android.onetachi.BuildConfig
import com.android.onetachi.protocol.RetrofitNetwork
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.*
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.*

class MyRetrofit {
    var retrofit = Retrofit.Builder().baseUrl("https://210.107.78.156:443")
        .client(getUnsafeOkHttpClient()?.build())
        /*.client(
            OkHttpClient.Builder()
                .sslSocketFactory(getPinnedCertSslSocketFactory(this))   //ssl
                .hostnameVerifier(NullHostNameVerifier())                //ssl HostName Pass
                .build())*/
        .addConverterFactory(GsonConverterFactory.create())
        .build();

    public var service = retrofit.create(RetrofitNetwork::class.java)

    /*
     * private function
     */
    // 안전하지 않음으로 HTTPS를 통과합니다.
    private fun getUnsafeOkHttpClient(): OkHttpClient.Builder? {
        return try {
            val trustAllCerts = arrayOf<TrustManager>(
                object : X509TrustManager {
                    @Throws(CertificateException::class)
                    override fun checkClientTrusted(
                        chain: Array<X509Certificate?>?,
                        authType: String?
                    ) {
                    }

                    //@Throws(CertificateException::class)
                    override fun checkServerTrusted(
                        chain: Array<X509Certificate?>?,
                        authType: String?
                    ) {
                    }

                    override fun getAcceptedIssuers(): Array<X509Certificate?>? {
                        return arrayOf()
                    }
                }
            )
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())
            val sslSocketFactory = sslContext.socketFactory
            val builder = OkHttpClient.Builder()
            builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            builder.hostnameVerifier { hostname, session -> true }
            builder
        } catch (e: java.lang.Exception) {
            throw RuntimeException(e)
        }
    }

    /**
     * localhost라는 인증서 정보로 ssl 연결을시도합니다.
     * @param context
     * @return
     */
    /*private fun getPinnedCertSslSocketFactory(context: Context): SSLSocketFactory? {
        try {
            val cf =
                CertificateFactory.getInstance("X.509")
            val caInput =
                context.resources.openRawResource(R.raw.localhost)
            var ca: Certificate? = null
            try {
                ca = cf.generateCertificate(caInput)
            } catch (e: CertificateException) {
                e.printStackTrace()
            } finally {
                caInput.close()
            }
            val keyStoreType = KeyStore.getDefaultType()
            val keyStore = KeyStore.getInstance(keyStoreType)
            keyStore.load(null, null)
            if (ca == null) {
                return null
            }
            keyStore.setCertificateEntry("ca", ca)
            val tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm()
            val tmf = TrustManagerFactory.getInstance(tmfAlgorithm)
            tmf.init(keyStore)
            val sslContext = SSLContext.getInstance("TLS")
            sslContext.init(null, tmf.trustManagers, null)
            return sslContext.socketFactory
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: KeyStoreException) {
            e.printStackTrace()
        } catch (e: KeyManagementException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }*/

   /* private class NullHostNameVerifier : HostnameVerifier {
        @Override
        override fun verify(hostname: String?, session: SSLSession?): Boolean {
            return true
        }
    }*/
}