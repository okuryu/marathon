[![Stories in Ready](https://badge.waffle.io/mesosphere/marathon.png?label=ready&title=Ready)](https://waffle.io/mesosphere/marathon)
# [Marathon](https://mesosphere.github.io/marathon/) [![Build Status](https://travis-ci.org/mesosphere/marathon.png?branch=master)](https://travis-ci.org/mesosphere/marathon) [![Coverage Status](https://coveralls.io/repos/mesosphere/marathon/badge.svg?branch=master)](https://coveralls.io/r/mesosphere/marathon?branch=master)

Marathon is an [Apache Mesos][Mesos] framework for long-running applications. Given that
you have Mesos running as the kernel for your datacenter, Marathon is the
[`init`][init] or [`upstart`][upstart] daemon.

Marathon provides a
[REST API](https://mesosphere.github.io/marathon/docs/rest-api.html) for
starting, stopping, and scaling applications. Marathon is written in Scala and
can run in highly-available mode by running multiple copies. The
state of running tasks gets stored in the Mesos state abstraction.

Marathon is a *meta framework*: you can start other Mesos frameworks such as
Chronos or [Storm][Storm] with it to ensure they survive machine failures.
It can launch anything that can be launched in a standard shell. In fact, you
can even start other Marathon instances via Marathon.

Using Marathon versions 0.7.0+ and Mesos 0.20.0+, you can [deploy, run and scale Docker containers](https://mesosphere.github.io/marathon/docs/native-docker.html) with ease.

## Features

* *HA* -- run any number of Marathon schedulers, but only one gets elected as
    leader; if you access a non-leader, your request gets proxied to the
    current leader
* *[Constraints](https://mesosphere.github.io/marathon/docs/constraints.html)* - e.g., only one instance of an application per rack, node, etc.
* *[Service Discovery &amp; Load Balancing](https://mesosphere.github.io/marathon/docs/service-discovery-load-balancing.html)* via HAProxy or the events API (see below).
* *[Health Checks](https://mesosphere.github.io/marathon/docs/health-checks.html)*: check your application's health via HTTP or TCP checks.
* *[Event Subscription](https://mesosphere.github.io/marathon/docs/rest-api.html#event-subscriptions)* lets you supply an HTTP endpoint to receive notifications, for example to integrate with an external load balancer.
* *Web UI*
* *[JSON/REST API](https://mesosphere.github.io/marathon/docs/rest-api.html)* for easy integration and scriptability
* *Basic Auth* and *SSL*
* *Metrics*: available at `/metrics` in JSON format

## Documentation

Marathon documentation is available on the [Marathon GitHub pages site](http://mesosphere.github.io/marathon/).

Documentation for installing and configuring the full Mesosphere stack including Mesos and Marathon is available on the [Mesosphere website](http://docs.mesosphere.com).

### Contributing

We heartily welcome external contributions to Marathon's documentation. Documentation should be committed to the `master` branch and published to our GitHub pages site using the instructions in [docs/README.md](https://github.com/mesosphere/marathon/tree/master/docs).

## Setting Up And Running Marathon

### Installation

#### Install Mesos

Marathon requires Mesos installed on the same machine in order to use a shared library. Instructions on how to install prepackaged releases of Mesos are available [in the Marathon docs](https://mesosphere.github.io/marathon/docs/).

#### Install Marathon

Instructions on how to install prepackaged releases are available [in the Marathon docs](https://mesosphere.github.io/marathon/docs/). Alternatively, you can build Marathon from source.

##### Building from Source

1.  To build Marathon from source, check out this repo and submodules and use sbt to build a JAR:

        git clone --recursive https://github.com/mesosphere/marathon.git
        cd marathon
        sbt assembly

1.  Run `./bin/build-distribution` to package Marathon as an
    [executable JAR](http://mesosphere.com/2013/12/07/executable-jars/)
    (optional).

### Running in Development Mode

Mesos local mode allows you to run Marathon without launching a full Mesos
cluster. It is meant for experimentation and not recommended for production
use. Note that you still need to run ZooKeeper for storing state. The following
command launches Marathon on Mesos in *local mode*. Point your web browser to
`http://localhost:8080` to see the Marathon UI.

    ./bin/start --master local --zk zk://localhost:2181/marathon

For more information on how to run Marathon in production and configuration
options, see [the Marathon docs](https://mesosphere.github.io/marathon/docs/).

## Developing Marathon

See [the documentation](https://mesosphere.github.io/marathon/docs/developing-vm.html) on how to run Marathon locally inside a virtual machine.

### Running the development Docker

Build tip:

    docker build -t marathon-head .

Run it:

    docker run marathon-head --master local --zk zk://localhost:2181/marathon

If you want to inspect the contents of the Docker:

    docker run -i -t --entrypoint=/bin/bash marathon-head -s

### Marathon UI

To develop on the web UI look into the instructions of the [Marathon UI](https://github.com/mesosphere/marathon-ui) repository.

## Marathon Clients

* [marathonctl](https://github.com/shoenig/marathonctl) A handy CLI tool for controlling Marathon
* [Ruby gem and command line client](https://rubygems.org/gems/marathon-api)

    Running Chronos with the Ruby Marathon Client:

        marathon -M http://foo.bar:8080 start -i chronos -u https://s3.amazonaws.com/mesosphere-binaries-public/chronos/chronos.tgz \
            -C "./chronos/bin/demo ./chronos/config/nomail.yml \
            ./chronos/target/chronos-1.0-SNAPSHOT.jar" -c 1.0 -m 1024
* [Scala client](https://github.com/guidewire/marathon-client), developed at Guidewire
* [Java client](https://github.com/mohitsoni/marathon-client) by Mohit Soni
* [Maven plugin](https://github.com/holidaycheck/marathon-maven-plugin), developed at [HolidayCheck](http://www.holidaycheck.com/)
* [Python client](https://github.com/thefactory/marathon-python), developed at [The Factory](http://www.thefactory.com)
* [Python client](https://github.com/Wizcorp/marathon-client.py), developed at [Wizcorp](http://www.wizcorp.jp)
* [Go client](https://github.com/gambol99/go-marathon) by Rohith Jayawardene
* [Go client](https://github.com/jbdalido/gomarathon) by Jean-Baptiste Dalido
* [Node client](https://github.com/silas/node-mesos) by Silas Sewell
* [Clojure client](https://github.com/codemomentum/marathonclj) by Halit Olali

## Companies using Marathon

Across all installations Marathon is managing applications on more than 100,000 nodes world-wide. These are some of the companies using it:

* [Airbnb](https://www.airbnb.com/)
* [Allegro Group](http://www.allegrogroup.com)
* [Artirix](http://www.artirix.com/)
* [Corvisa](https://www.corvisa.com/)
* [bol.com](https://www.bol.com/)
* [Branding Brand](http://www.brandingbrand.com/)
* [Daemon](http://www.daemon.com.au/)
* [DHL Parcel](http://www.dhlparcel.nl)
* [Disqus](https://www.disqus.com/)
* [eBay](http://www.ebay.com/)
* [The Factory](https://github.com/thefactory/)
* [Football Radar](http://www.footballradar.com)
* [Guidewire](http://www.guidewire.com/)
* [Groupon](http://www.groupon.com/)
* [GSShop](http://www.gsshop.com/)
* [HolidayCheck](http://www.holidaycheck.com/)
* [Human API](https://humanapi.co/)
* [Indix](http://www.indix.com/)
* [ING](http://www.ing.com/)
* [iQIYI](http://www.iqiyi.com/)
* [Measurence](http://www.measurence.com/)
* [Motus](http://www.motus.com/)
* [OpenTable](http://www.opentable.com/)
* [Orbitz](http://www.orbitz.com/)
* [Otto](https://www.otto.de/)
* [PayPal](https://www.paypal.com)
* [Qubit](http://www.qubitproducts.com/)
* [RelateIQ](http://relateiq.com/)
* [Refinery29](https://www.refinery29.com)
* [Sailthru](http://www.sailthru.com/)
* [sloppy.io](http://sloppy.io/)
* [SmartProcure](https://smartprocure.us/)
* [Strava](https://www.strava.com)
* [Viadeo](http://www.viadeo.com)
* [Wikia](http://www.wikia.com)
* [WooRank](http://www.woorank.com)
* [Yelp](http://www.yelp.com/)

Not in the list? Open a pull request and add yourself!

## Help

Have you found an issue? Feel free to report it using our [Issues](https://github.com/mesosphere/marathon/issues) page.
In order to speed up response times, we ask you to provide as much
information on how to reproduce the problem as possible. If the issue is related
 in any way to the web ui, we kindly ask you to use the `gui` label.

If you have questions, please post on the
[Marathon Framework Group](https://groups.google.com/forum/?hl=en#!forum/marathon-framework)
email list. You can find Marathon support in the `#marathon` channel, and Mesos
support in the `#mesos` channel, on [freenode][freenode] (IRC). The team at
[Mesosphere][Mesosphere] is also happy to answer any questions.

## Authors

Marathon was created by [Tobias Knaup](https://github.com/guenter) and
[Florian Leibert](https://github.com/florianleibert) and continues to be
developed by the team at Mesosphere and by many contributors from
the community.

[Chronos]: https://github.com/airbnb/chronos "Airbnb's Chronos"
[Mesos]: https://mesos.apache.org/ "Apache Mesos"
[Zookeeper]: https://zookeeper.apache.org/ "Apache Zookeeper"
[Storm]: http://storm-project.net/ "distributed realtime computation"
[freenode]: https://freenode.net/ "IRC channels"
[upstart]: http://upstart.ubuntu.com/ "Ubuntu's event-based daemons"
[init]: https://en.wikipedia.org/wiki/Init "init"
[Mesosphere]: http://mesosphere.com/ "Mesosphere"
