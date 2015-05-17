import sbt._
import Keys._
import org.scalatra.sbt._
import org.scalatra.sbt.PluginKeys._
import com.mojolly.scalate.ScalatePlugin._
import ScalateKeys._

object AlexandriaBuild extends Build {
  val Organization = "me.hawkweisman"
  val Name = "alexandria"
  val Version = "0.0.1"
  val ScalaVersion = "2.11.6"
  val ScalatraVersion = "maven(org.scalatra, scalatra_2.11)"
  val SlickVersion = "3.0.0"

  lazy val project = Project (
    "alexandria",
    file("."),
    settings = ScalatraPlugin.scalatraWithJRebel ++ scalateSettings ++ Seq(
      organization := Organization,
      name := Name,
      version := Version,
      scalaVersion := ScalaVersion,
      resolvers += Classpaths.typesafeReleases,
      resolvers += "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases",
      libraryDependencies ++= Seq(
        "org.scalatra"                  %%  "scalatra"          % ScalatraVersion,
        "org.scalatra"                  %%  "scalatra-scalate"  % ScalatraVersion,
        "org.scalatra"                  %%  "scalatra-specs2"   % ScalatraVersion % "test",
        "org.scalatra"                  %%  "scalatra-json"     % ScalatraVersion,
        "org.scalatra"                  %%  "scalatra-auth"     % ScalatraVersion,
        "org.json4s"                    %%  "json4s-jackson"    % "3.2.11",
        "com.typesafe.slick"            %%  "slick"             % SlickVersion,
        "com.typesafe.scala-logging"    %%  "scala-logging"     % "3.1.0",
        "com.h2database"                %   "h2"                % "1.3.166",
        "c3p0"                          %   "c3p0"              % "0.9.1.2",
        "ch.qos.logback"                %   "logback-classic"   % "1.1.2" % "runtime",
        "org.eclipse.jetty"             %   "jetty-webapp"      % "9.1.5.v20140505" % "container",
        "org.eclipse.jetty"             %   "jetty-plus"        % "9.1.5.v20140505" % "container",
        "javax.servlet"                 %   "javax.servlet-api" % "3.1.0"
      ),
      scalateTemplateConfig in Compile <<= (sourceDirectory in Compile){ base =>
        Seq(
          TemplateConfig(
            base / "webapp" / "WEB-INF" / "templates",
            Seq.empty,  /* default imports should be added here */
            Seq(
              Binding("context", "_root_.org.scalatra.scalate.ScalatraRenderContext", importMembers = true, isImplicit = true)
            ),  /* add extra bindings here */
            Some("templates")
          )
        )
      }
    )
  )
}