package mesosphere.marathon.plugin

import java.net.URL

import com.google.inject.{ Singleton, Provides, AbstractModule }
import org.rogach.scallop.ScallopConf

trait PluginConfiguration extends ScallopConf {
  lazy val pluginUris = opt[List[String]]("plugin_uris",
    descr = "List of URIs containing plugin jars",
    noshort = true
  )
}

class PluginModule(conf: PluginConfiguration) extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[PluginConfiguration]).toInstance(conf)
  }

  @Provides
  @Singleton
  def providePluginManager(conf: PluginConfiguration): PluginManager = {
    conf.pluginUris.get.map { pluginUris: List[String] =>
      val urls: Seq[URL] = pluginUris.map(new URL(_))
      new PluginManager(urls)
    }.getOrElse {
      new PluginManager(Seq.empty)
    }
  }
}
