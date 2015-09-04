package mesosphere.marathon.plugin

import java.net.{ URL, URLClassLoader }
import java.util.ServiceLoader

import org.slf4j.{ Logger, LoggerFactory }

import scala.collection.JavaConverters._
import scala.reflect.ClassTag

/**
  * Instances of this class holds a reference to a plugin of a specific type.
  * @param classTag the class tag of the specific type
  * @param plugins all the instances of this plugin
  * @tparam T the specific type of this plugin.
  */
case class PluginReference[T](classTag: ClassTag[T], plugins: Seq[T])

/**
  * The plugin manager can load plugins from given urls.
  * @param urls the urls pointing to plugins.
  */
class PluginManager(val urls: Seq[URL]) {

  private[this] val log: Logger = LoggerFactory.getLogger(getClass)

  private[this] var pluginReferences: List[PluginReference[_]] = List.empty[PluginReference[_]]

  val classLoader: URLClassLoader = new URLClassLoader(urls.toArray, this.getClass.getClassLoader)

  /**
    * Load plugin for a specific type.
    */
  private[this] def load[T](implicit ct: ClassTag[T]): PluginReference[T] = {
    val serviceLoader = ServiceLoader.load(ct.runtimeClass.asInstanceOf[Class[T]], classLoader)
    val plugins = serviceLoader.iterator().asScala.toSeq
    log.info(
      s"""Loaded plugins implementing '${ct.runtimeClass.getName}' from these locations: '[${urls.mkString(", ")}]'.
         || Found ${plugins.size} plugins.""")
    PluginReference(ct, plugins)
  }

  /**
    * Get all the service providers that can be found in the plugin directory for the given type.
    * Each plugin type is loaded once and gets cached.
    * @return the list of all service providers for the given type.
    */
  def plugins[T](implicit sc: ClassTag[T]): Seq[T] = synchronized {
    def loadAndAdd: PluginReference[T] = {
      val pluginReference: PluginReference[T] = load[T]
      pluginReferences ::= pluginReference
      pluginReference
    }

    pluginReferences
      .find(_.classTag == sc)
      .map(_.asInstanceOf[PluginReference[T]])
      .getOrElse(loadAndAdd)
      .plugins
  }

  def installedPlugins: Seq[PluginReference[_]] = pluginReferences
}
