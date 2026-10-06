package com.example

import com.example.model.LearningMaterials
import com.example.model.QuizRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.math.abs
import kotlin.math.tan

class TrigonometriUnitTest {

    @Test
    fun testQuestionsCountAndFormat() {
        val questions = QuizRepository.questions
        assertEquals(10, questions.size)

        questions.forEachIndexed { index, q ->
            assertEquals(index + 1, q.id)
            assertEquals(5, q.options.size)
            assertTrue(q.correctIndex in 0..4)
            assertTrue(q.title.isNotBlank())
            assertTrue(q.contextStory.isNotBlank())
            assertTrue(q.explanation.isNotBlank())
        }
    }

    @Test
    fun testMaterialsCount() {
        val materials = LearningMaterials.sections
        assertEquals(5, materials.size)
    }

    @Test
    fun testCoconutTreeSimulationCalculation() {
        // Observer = 1.6m, distance = 15.0m, angle = 45 degrees
        // tan(45°) = 1.0 => total tree height = 1.6 + (15.0 * 1.0) = 16.6m
        val d = 15.0
        val obs = 1.6
        val angleDeg = 45.0
        val height = obs + d * tan(Math.toRadians(angleDeg))
        assertTrue(abs(height - 16.6) < 0.001)
    }
}
