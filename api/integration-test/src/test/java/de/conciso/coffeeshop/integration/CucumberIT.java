package de.conciso.coffeeshop.integration;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = {"classpath:specification"},
    glue = {"de.berbel.configurator.integration"},
    snippets = CucumberOptions.SnippetType.CAMELCASE,
    strict = true)
public final class CucumberIT {
}
