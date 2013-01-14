package util

import play.api.mvc.WrappedRequest
import play.api.mvc.Request

import org.squeryl.dsl.GroupWithMeasures

import models.Stakeholder

/**
 * @author  Dr. Erich W. Schreiner - Software Design &amp; Consulting GmbH
 * @version 0.1.0.0
 * @since   0.1.0.0
 */
case class Context[A](request: Request[A],
        stats: List[GroupWithMeasures[String,Long]],
        user: Option[Stakeholder])
extends WrappedRequest(request)
